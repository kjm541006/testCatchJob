import React, { useEffect, useState } from "react";
import styles from "../assets/css/BuildStudy.module.css";

const BuildStudyPage = () => {
  const [bType, setBType] = useState("project");
  const [selectedField, setSelectedField] = useState(null);
  const [selectedTerm, setSelectedTerm] = useState(null);
  const [selectedPlatform, setSelectedPlatform] = useState(null);
  const [selectedLoc, setSelectedLoc] = useState(null);
  const [crewList, setCrewList] = useState([{ id: 1, count: 1 }]);

  const changeTypeToProject = () => {
    setBType("project");
  };

  const changeTypeToStudy = () => {
    setBType("study");
  };

  const handleFieldChange = (option) => {
    setSelectedField(option);
  };

  const handleTermChange = (option) => {
    setSelectedTerm(option);
  };

  const handlePlatformChange = (option) => {
    setSelectedPlatform(option);
  };

  const handleLocChange = (option) => {
    setSelectedLoc(option);
  };

  const addNewToList = (e) => {
    e.preventDefault();
    const newCrew = { id: Date.now() };
    setCrewList((prevList) => [...prevList, newCrew]);
  };

  useEffect(() => {
    console.log(selectedField);
    console.log(selectedTerm);
    console.log(selectedPlatform);
  }, [selectedField, selectedTerm, selectedPlatform]);

  return (
    <div className={styles.buildPage}>
      <form method="post">
        <div className={styles.buildType}>
          <div className={`${styles.type} ${bType === "project" && styles.active}`} onClick={changeTypeToProject}>
            프로젝트
          </div>
          <div className={` ${styles.type} ${bType === "study" && styles.active}`} onClick={changeTypeToStudy}>
            스터디
          </div>
        </div>

        {/* =====================프로젝트 명======================= */}
        <div className={styles.wrapper}>
          <div className={styles.title}>프로젝트 명</div>
          <input type="text" name="title" placeholder="제목" className={styles.titleInput} />
        </div>

        {/* =====================프로젝트 분야======================= */}
        <div className={styles.wrapper}>
          <div className={styles.title}>프로젝트 분야</div>
          <ul className={styles.items}>
            <li>
              <input
                type="radio"
                name="field"
                id="fashion"
                value="패션"
                checked={selectedField === "패션"}
                onChange={() => handleFieldChange("패션")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="fashion">
                <div className={`${styles.select} ${selectedField === "패션" && styles.active} `}>패션</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="field"
                id="financial"
                value="금융"
                checked={selectedField === "금융"}
                onChange={() => handleFieldChange("금융")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="financial">
                <div className={`${styles.select} ${selectedField === "금융" && styles.active} `}>금융</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="field"
                id="sports"
                value="스포츠"
                checked={selectedField === "스포츠"}
                onChange={() => handleFieldChange("스포츠")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="sports">
                <div className={`${styles.select} ${selectedField === "스포츠" && styles.active} `}>스포츠</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="field"
                id="travel"
                value="여행"
                checked={selectedField === "여행"}
                onChange={() => handleFieldChange("여행")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="travel">
                <div className={`${styles.select} ${selectedField === "여행" && styles.active} `}>여행</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="field"
                id="others"
                value="기타"
                checked={selectedField === "기타"}
                onChange={() => handleFieldChange("기타")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="others">
                <div className={`${styles.select} ${selectedField === "기타" && styles.active} `}>기타</div>
              </label>
            </li>
          </ul>
        </div>

        {/* =====================프로젝트 기간======================= */}
        <div className={styles.wrapper}>
          <div className={styles.title}>프로젝트 기간</div>
          <ul className={styles.items}>
            <li>
              <input
                type="radio"
                name="term"
                id="1"
                value="1개월"
                checked={selectedField === "1개월"}
                onChange={() => handleTermChange("1개월")}
                className={styles.radioBtn}
              />
              <label className={styles.termSelect} htmlFor="1">
                <div className={`${styles.select} ${selectedTerm === "1개월" && styles.active} `}>1개월</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="term"
                id="2"
                value="2개월"
                checked={selectedField === "2개월"}
                onChange={() => handleTermChange("2개월")}
                className={styles.radioBtn}
              />
              <label className={styles.termSelect} htmlFor="2">
                <div className={`${styles.select} ${selectedTerm === "2개월" && styles.active} `}>2개월</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="term"
                id="3"
                value="3개월"
                checked={selectedField === "3개월"}
                onChange={() => handleTermChange("3개월")}
                className={styles.radioBtn}
              />
              <label className={styles.termSelect} htmlFor="3">
                <div className={`${styles.select} ${selectedTerm === "3개월" && styles.active} `}>3개월</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="term"
                id="6"
                value="6개월"
                checked={selectedField === "6개월"}
                onChange={() => handleTermChange("6개월")}
                className={styles.radioBtn}
              />
              <label className={styles.termSelect} htmlFor="6">
                <div className={`${styles.select} ${selectedTerm === "6개월" && styles.active} `}>6개월</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="term"
                id="12"
                value="1년 이상"
                checked={selectedField === "1년 이상"}
                onChange={() => handleTermChange("1년 이상")}
                className={styles.radioBtn}
              />
              <label className={styles.termSelect} htmlFor="12">
                <div className={`${styles.select} ${selectedTerm === "1년 이상" && styles.active} `}>1년 이상</div>
              </label>
            </li>
          </ul>
        </div>

        {/* ===================== 지역 ======================= */}
        <div className={styles.wrapper}>
          <div className={styles.title}>지역</div>
          <ul className={styles.items}>
            <li>
              <input
                type="radio"
                name="loc"
                id="online"
                value="온라인"
                checked={selectedLoc === "온라인"}
                onChange={() => handleLocChange("온라인")}
                className={styles.radioBtn}
              />
              <label className={styles.locSelect} htmlFor="online">
                <div className={`${styles.select} ${selectedLoc === "온라인" && styles.active} `}>온라인</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                // name="loc"
                id="offline"
                value="오프라인"
                checked={selectedLoc === "오프라인"}
                onChange={() => handleLocChange("오프라인")}
                className={styles.radioBtn}
              />
              <label className={styles.locSelect} htmlFor="offline">
                <div className={`${styles.select} ${selectedLoc === "오프라인" && styles.active} `}>오프라인</div>
              </label>
            </li>
            {selectedLoc === "오프라인" && (
              <select name="loc" className={styles.selects}>
                <option value="서울">서울특별시</option>
                <option value="경기">경기도</option>
                <option value="부산">부산광역시</option>
                <option value="인천">인천광역시</option>
                <option value="대구">대구광역시</option>
                <option value="충청">충청도</option>
                <option value="경상">경상도</option>
                <option value="강원">강원도</option>
                <option value="전라">전라도</option>
                <option value="제주">제주도</option>
              </select>
            )}
          </ul>
        </div>

        {/* =====================모집인원======================= */}
        <div className={styles.wrapper}>
          <div className={styles.title}>모집인원</div>
          <div className={styles.crewList}>
            {crewList.map((item) => (
              <ul className={`${styles.items} ${styles.crewItems}`}>
                <li key={item.id}>
                  <select name="crew" id="crew" className={styles.selects}>
                    <option value="웹디자인">웹디자인</option>
                    <option value="프론트엔드">프론트엔드</option>
                    <option value="백엔드">백엔드</option>
                  </select>
                </li>
                <div className={styles.plusMinusBtn}>
                  <button className={styles.minusBtn}>-</button>
                  <div className={styles.countNum}>1</div>
                  <button className={styles.plusBtn}>+</button>
                </div>
              </ul>
            ))}
          </div>
          <div className={styles.btnContainer}>
            <button onClick={addNewToList} className={styles.crewAddBtn}>
              추가
            </button>
          </div>
        </div>

        {/* =====================출시 플랫폼======================= */}
        <div className={styles.wrapper}>
          <div className={styles.title}>출시 플랫폼</div>
          <ul className={styles.items}>
            <li>
              <input
                type="radio"
                name="platform"
                id="web"
                value="웹"
                checked={selectedPlatform === "웹"}
                onChange={() => handlePlatformChange("웹")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="web">
                <div className={`${styles.select} ${selectedPlatform === "웹" && styles.active} `}>웹</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="platform"
                id="android"
                value="안드로이드 앱"
                checked={selectedPlatform === "안드로이드 앱"}
                onChange={() => handlePlatformChange("안드로이드 앱")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="android">
                <div className={`${styles.select} ${selectedPlatform === "안드로이드 앱" && styles.active} `}>안드로이드 앱</div>
              </label>
            </li>
            <li>
              <input
                type="radio"
                name="platform"
                id="ios"
                value="ios 앱"
                checked={selectedPlatform === "ios 앱"}
                onChange={() => handlePlatformChange("ios 앱")}
                className={styles.radioBtn}
              />
              <label className={styles.fieldSelect} htmlFor="ios">
                <div className={`${styles.select} ${selectedPlatform === "ios 앱" && styles.active} `}>ios 앱</div>
              </label>
            </li>
          </ul>
        </div>
      </form>
    </div>
  );
};

export default BuildStudyPage;
