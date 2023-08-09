// const axios = require("axios");

// exports.handler = async (event, context) => {
//   try {
//     const response = await axios({
//       method: event.httpMethod,
//       url: "http://43.202.98.45:8089", // 여기에 실제 요청할 API 주소를 입력하세요.
//       headers: { ...event.headers },
//       data: event.body,
//     });

//     return {
//       statusCode: response.status,
//       headers: {
//         ...response.headers,
//         "Content-Type": "application/json", //응답 타입을 명시적으로 지정합니다.
//       },
//       body: JSON.stringify(response.data),
//     };
//   } catch (error) {
//     return {
//       statusCode: 500,
//       headers: {
//         "Content-Type": "application/json", //응답타입을 명시적으로 지정합니다.
//       },
//       body: JSON.stringify({ error: error.message }),
//     };
//   }
// };

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
