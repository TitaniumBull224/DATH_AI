// Generate a random clientId
const clientId = "mqttjs_" + Math.random().toString(16).substr(2, 8);

// Set the host and options for the MQTT connection
const host = "wss://io.adafruit.com:443";
const options = {
  clean: true,
  connectTimeout: 4000,
  clientId: clientId,
  username: "nhanchucqt", // Set your username here
  password: "aio_GTvf53NrJNbco4Knw3IvBKIBOdwI", // Set your password here
};

// Create an MQTT client
const client = mqtt.connect(host, options);

// Check if the connection is successful
function mqtt_check_connection() {
  client.on("connect", function () {
    console.log("Connected");
  });
}

// Check for incoming messages
function mqtt_check_message() {
  client.on("message", (topic, message, packet) => {
    console.log(
      "Received Message: " + message.toString() + " on topic: " + topic
    );
  });
}

// Subscribe to a topic
function mqtt_subscribe(topic) {
  client.subscribe(
    options.username + "feeds/" + topic,
    { qos: 1 },
    function (error, granted) {
      if (error) {
        console.log(error);
      } else {
        console.log(`${granted[0].topic} was subscribed`);
      }
    }
  );
}

// Publish a message to a topic
function mqtt_publish(topic, msg) {
  client.publish(
    options.username + "feeds/" + topic,
    msg,
    { qos: 1, retain: false },
    function (error) {
      if (error) {
        console.log(error);
      } else {
        console.log("Published");
      }
    }
  );
}

// Setup canvas and audio stream
async function setup() {
  createCanvas(
    window.parent.document.getElementById("js-runner-container").offsetWidth -
      50,
    window.parent.document.getElementById("js-runner-container").offsetHeight -
      50
  );
  mqtt_check_connection();
  console.log("Press 'r' to start recording");
}

// Start recording when the user presses the 'r' key
function keyPressed() {
  if (key === "r" || key === "R") {
    startRecording();
  }
}

// Start recording the audio
async function startRecording() {
  console.log("Recording started");
  try {
    const audioStream = await getMicrophoneStream();
    classifySound(audioStream);
  } catch (error) {
    console.error("Error getting microphone stream:", error);
  }
}

// Classify sound from the microphone
async function classifySound(audioStream) {
  if (!parent || Boolean(parent.isStopCode)) {
    return;
  }

  console.log("Start sound prediction");

  // Record audio from the user's microphone
  const recorder = new p5.SoundRecorder();
  const soundFile = new p5.SoundFile();
  recorder.setInput(audioStream);
  recorder.record(soundFile);

  // Record for 5 seconds
  await new Promise((resolve) => setTimeout(resolve, 5000));

  // Stop recording
  recorder.stop();

  // Convert the recorded audio to a WAV format Blob
  const audioBlob = await new Promise((resolve) => {
    soundFile.getBlob((blob) => {
      resolve(new Blob([blob], { type: "audio/wav" }));
    });
  });

  // Send the audio to the OpenAI API to transcribe it into text
  try {
    const transcribedText = await transcribeAudioWithOpenAI(audioBlob);

    // Log the transcribed text to the console
    console.log("Transcribed text:", transcribedText);

    // Trigger commands based on the transcribed text
    switch (transcribedText) {
      case "bật quạt":
        mqtt_publish("quat", "1");
        break;
      case "tắt quạt":
        mqtt_publish("quat", "0");
        break;
      case "bật đèn":
        mqtt_publish("den", "1");
        break;
      case "tắt đèn":
        mqtt_publish("den", "0");
        break;
    }
  } catch (error) {
    console.error("Error transcribing audio:", error);
  }
}

// Get microphone stream
async function getMicrophoneStream() {
  try {
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    return stream;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

// This function sends audio to the OpenAI API to transcribe it into text
async function transcribeAudioWithOpenAI(audioBlob) {
  // OpenAI API key
  const OPENAI_API_KEY = "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";
  // URI of the OpenAI API
  const uri = "https://api.openai.com/v1/speech/recognitions";

  // Read the audio blob as an array buffer
  const buffer = await new Promise((resolve) => {
    const reader = new FileReader();
    reader.readAsArrayBuffer(audioBlob);
    reader.onloadend = () => resolve(reader.result);
  });

  // Convert the array buffer to base64
  const base64Audio = btoa(
    new Uint8Array(buffer).reduce(
      (data, byte) => data + String.fromCharCode(byte),
      ""
    )
  );

  try {
    // Send a POST request to the OpenAI API with the audio data
    const response = await fetch(uri, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${OPENAI_API_KEY}`,
      },
      body: JSON.stringify({
        audio: {
          content: base64Audio,
        },
        format: "WAV",
        sample_rate: 16000,
        language: "vi-VN", // Change the language to Vietnamese
      }),
    });

    // Get the transcription from the response
    const json = await response.json();
    return json.transcriptions[0].text;
  } catch (error) {
    console.error("Error during OpenAI API call:", error);
    throw error;
  }
}
