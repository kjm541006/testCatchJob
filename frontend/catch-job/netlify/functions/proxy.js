const axios = require("axios");

exports.handler = async (event, context) => {
  try {
    const response = await axios({
      method: event.httpMethod,
      url: `http://43.202.98.45:8089${event.path.replace("/.netlify/functions/proxy", "")}`,
      headers: { ...event.headers, "Content-Type": "application/json" },
      data: event.body,
    });

    return {
      statusCode: response.status,
      headers: {
        ...response.headers,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(response.data),
    };
  } catch (error) {
    console.error("Error in proxy function:", error.message);
    return {
      statusCode: 500,
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ error: error.message }),
    };
  }
};

// const axios = require("axios");

// exports.handler = async (event, context) => {
//   try {
//     const backendUrl = event.path.replace("/.netlify/functions/proxy/", "http://").replaceAll("%2F", "/");

//     const response = await axios({
//       method: event.httpMethod,
//       url: backendUrl,
//       headers: { ...event.headers, "Content-Type": "application/json" },
//       data: event.body,
//     });

//     return {
//       statusCode: response.status,
//       headers: {
//         ...response.headers,
//         "Content-Type": response.headers["content-type"],
//       },
//       body: JSON.stringify(response.data),
//       isBase64Encoded: Buffer.from(JSON.stringify(response.data), "binary").toString("base64"),
//     };
//   } catch (error) {
//     console.error("Error in proxy function:", error.message);
//     return {
//       statusCode: 500,
//       headers: {
//         "Content-Type": "application/json",
//       },
//       body: JSON.stringify({ error: error.message }),
//     };
//   }
// };
