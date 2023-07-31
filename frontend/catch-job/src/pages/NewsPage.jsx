import React, { useEffect, useState } from "react";
import axios from "axios";
import styles from "../assets/css/NewsPage.module.css";
function NewsPage() {
  const [data, setData] = useState(null);
  const [searchWord, setSearchWord] = useState("");

  useEffect(() => {}, []);
  const handleSearch = (e) => {
    setSearchWord(e.target.value);
  };

  const onClick = async () => {
    try {
      const ID_KEY = "Nr8FKE9N4Eqe8gY5XxvD";
      const SECRET_KEY = "Q7YFNvGmGv";
      const response = await axios.get(`/api/v1/search/news.json?query=${searchWord}&display=100&sort=sim`, {
        headers: {
          "X-Naver-Client-Id": ID_KEY,
          "X-Naver-Client-Secret": SECRET_KEY,
        },
      });

      console.log(response.data);
      setData(response.data);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className={styles.news}>
      <div>
        <input type="text" value={searchWord} onChange={handleSearch} />
        <button onClick={onClick}>검색</button>
      </div>
      {/* {data && <textarea rows={10} value={JSON.stringify(data, null, 2)} readOnly={true} />} */}
      {data &&
        data.items.map((v, i) => {
          return <div>${v.title}</div>;
        })}
    </div>
  );
}

export default NewsPage;
