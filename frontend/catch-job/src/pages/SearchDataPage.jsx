import { React, useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";

const SearchDataPage = () => {
  const [data, setData] = useState([]);
  const searchResults = useLocation().state?.searchResults || [];

  useEffect(() => {
    const fetchData = async () => {
      setData(searchResults);
    };
    fetchData();
  }, [searchResults]);

  return (
    <div>
      <h1>검색 결과</h1>
      <div>
        {data.map((item) => (
          <div key={item.boardId}>
            <h2>{item.bTitle}</h2>
            <p>{item.bContents}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SearchDataPage;
