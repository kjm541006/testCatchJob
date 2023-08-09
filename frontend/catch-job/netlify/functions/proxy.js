const axios = require("axios");

exports.handler = async (event, context) => {
  try {
    const response = await axios({
      method: event.httpMethod,
      url: "http://43.202.98.45:8089", // 여기에 실제 요청할 API 주소를 입력하세요.
      headers: event.headers,
      data: event.body,
    });

    return {
      statusCode: response.status,
      headers: response.headers,
      body: JSON.stringify(response.data),
    };
  } catch (error) {
    return {
      statusCode: 500,
      body: JSON.stringify({ error: error.message }),
    };
  }
};
