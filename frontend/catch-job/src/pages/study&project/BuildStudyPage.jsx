import React, { Fragment, useEffect, useRef, useState } from "react";
import styles from "../../assets/css/study/BuildStudy.module.css";
import axios from "axios";
import { useSelector } from "react-redux";
import { selectEmail } from "../../redux/login";
import { useNavigate } from "react-router-dom";

const BuildStudyPage = () => {
  const [bType, setBType] = useState("project");
  const [selectedField, setSelectedField] = useState(null);
  const [selectedTerm, setSelectedTerm] = useState(null);
  const [selectedPlatform, setSelectedPlatform] = useState([]);
  const [selectedLoc, setSelectedLoc] = useState("온라인");
  const [crewCounts, setCrewCounts] = useState({
    webDesigner: 0,
    webPublisher: 0,
    frontend: 0,
    backend: 0,
    PM: 0,
    others: 0,
  });
  const titleRef = useRef();
  const detail = useRef();
  const navigate = useNavigate();

  // const userEmail = localStorage.getItem("email");
  const userEmail = useSelector(selectEmail);

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
    if (selectedPlatform.includes(option)) {
      setSelectedPlatform(selectedPlatform.filter((p) => p !== option));
    } else {
      setSelectedPlatform([...selectedPlatform, option]);
    }
  };

  const handleLocChange = (event) => {
    const { value } = event.target;
    setSelectedLoc(value);
  };

  const handleInputChange = (event) => {
    const inputValue = event.target.value;
    const isValidInput = /^\d{0,2}$/.test(inputValue); // 숫자 0~2개인지 확인

    if (!isValidInput) {
      event.target.value = inputValue.slice(0, 2); // 입력값을 최대 2자리까지만 유지
    }

    const { name, value } = event.target;
    setCrewCounts({ ...crewCounts, [name]: parseInt(value, 10) });
  };

  const handleCancel = (e) => {
    e.preventDefault();
    window.location.href = "/study";
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const titleValue = titleRef.current.value;
    const detailValue = detail.current.value;
    const crewCountsArray = Object.values(crewCounts);
    const sumCrewCounts = crewCountsArray.reduce((a, b) => a + b, 0);

    if (!titleValue || !selectedField || !selectedTerm || !selectedPlatform || !selectedLoc || sumCrewCounts === 0 || !detailValue) {
      alert("모든 필드를 올바르게 입력해주세요.");
      return;
    }
    const buildData = {
      title: titleValue,
      field: selectedField,
      term: selectedTerm,
      platforms: selectedPlatform,
      loc: selectedLoc,
      crew: crewCounts,
      detail: detailValue,
      email: userEmail,
    };

    try {
      const response = await axios.post(`http://localhost:8089/build${bType}`, buildData); // JSON 데이터를 보내는 경우 'Content-Type': 'application/json' 헤더를 추가해야 합니다.
      console.log(response);
      if (response && response.status >= 200 && response.status < 300) {
        navigate(-1);
      }
    } catch (error) {
      // 에러가 발생한 경우
      console.error("에러가 발생했습니다.", error);
    }
  };

  useEffect(() => {
    console.log(selectedField);
    console.log(selectedTerm);
    console.log(selectedPlatform);
    console.log(selectedLoc);
    console.log(crewCounts);
  }, [selectedField, selectedTerm, selectedPlatform, selectedLoc, crewCounts]);

  return (
    <div className={styles.buildPage}>
      {/* <form method="post" action="http://localhost:8089/buildstudy" onSubmit={handleSubmit}> */}
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
        <input type="text" name="title" placeholder="제목" className={styles.titleInput} ref={titleRef} />
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
              onChange={handleLocChange}
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
              id="seoul"
              value="서울"
              checked={selectedLoc === "서울"}
              onChange={handleLocChange}
              className={styles.radioBtn}
            />
            <label className={styles.locSelect} htmlFor="seoul">
              <div className={`${styles.select} ${selectedLoc !== "온라인" && styles.active} `}>오프라인</div>
            </label>
          </li>
          {selectedLoc !== "온라인" && (
            <select name="loc" className={styles.selects} onChange={handleLocChange}>
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
        <ul className={styles.crewList}>
          <li className={styles.crewType}>
            <span className={styles.typeName}>웹디자인 : </span>
            <div className={styles.plusMinusBtn}>
              <div className={styles.countNum}>
                <input type="number" onChange={handleInputChange} name="webDesigner" defaultValue={0} />
                <span>명</span>
              </div>
            </div>
          </li>
          <li className={styles.crewType}>
            <span className={styles.typeName}>웹퍼블리셔 : </span>
            <div className={styles.plusMinusBtn}>
              <div className={styles.countNum}>
                <input type="number" onChange={handleInputChange} name="webPublisher" defaultValue={0} />
                <span>명</span>
              </div>
            </div>
          </li>
          <li className={styles.crewType}>
            <span className={styles.typeName}>프론트엔드 : </span>
            <div className={styles.plusMinusBtn}>
              <div className={styles.countNum}>
                <input type="number" onChange={handleInputChange} name="frontend" defaultValue={0} />
                <span>명</span>
              </div>
            </div>
          </li>
          <li className={styles.crewType}>
            <span className={styles.typeName}>백엔드 : </span>
            <div className={styles.plusMinusBtn}>
              <div className={styles.countNum}>
                <input type="number" onChange={handleInputChange} name="backend" defaultValue={0} />
                <span>명</span>
              </div>
            </div>
          </li>
          <li className={styles.crewType}>
            <span className={styles.typeName}>PM : </span>
            <div className={styles.plusMinusBtn}>
              <div className={styles.countNum}>
                <input type="number" onChange={handleInputChange} name="PM" defaultValue={0} />
                <span>명</span>
              </div>
            </div>
          </li>
          <li className={styles.crewType}>
            <span className={styles.typeName}>기타 : </span>
            <div className={styles.plusMinusBtn}>
              <div className={styles.countNum}>
                <input type="number" onChange={handleInputChange} name="others" defaultValue={0} />
                <span>명</span>
              </div>
            </div>
          </li>
        </ul>
      </div>

      {/* =====================출시 플랫폼======================= */}
      <div className={styles.wrapper}>
        <div className={styles.title}>출시 플랫폼</div>
        <ul className={styles.items}>
          <li>
            <input
              type="checkbox"
              name="platform"
              id="web"
              value="웹"
              checked={selectedPlatform.includes("웹")}
              onChange={() => handlePlatformChange("웹")}
              className={styles.radioBtn}
            />
            <label className={styles.fieldSelect} htmlFor="web">
              <div className={`${styles.select} ${selectedPlatform.includes("웹") && styles.active} `}>웹</div>
            </label>
          </li>
          <li>
            <input
              type="checkbox"
              name="platform"
              id="android"
              value="안드로이드 앱"
              checked={selectedPlatform.includes("안드로이드 앱")}
              onChange={() => handlePlatformChange("안드로이드 앱")}
              className={styles.radioBtn}
            />
            <label className={styles.fieldSelect} htmlFor="android">
              <div className={`${styles.select} ${selectedPlatform.includes("안드로이드 앱") && styles.active} `}>안드로이드 앱</div>
            </label>
          </li>
          <li>
            <input
              type="checkbox"
              name="platform"
              id="ios"
              value="ios 앱"
              checked={selectedPlatform.includes("ios 앱")}
              onChange={() => handlePlatformChange("ios 앱")}
              className={styles.radioBtn}
            />
            <label className={styles.fieldSelect} htmlFor="ios">
              <div className={`${styles.select} ${selectedPlatform.includes("ios 앱") && styles.active} `}>ios 앱</div>
            </label>
          </li>
        </ul>
      </div>

      {/* =====================프로젝트 설명======================= */}
      <div className={styles.wrapper}>
        <div className={styles.title}>프로젝트 설명</div>
        <textarea
          name="projectDetail"
          id=""
          cols="100"
          rows="10"
          placeholder="내용을 입력하세요."
          className={styles.projectTextarea}
          ref={detail}
        ></textarea>
      </div>

      <div className={styles.wrapper}>
        <div className={styles.submitForm}>
          <button className={styles.back} onClick={handleCancel}>
            뒤로 가기
          </button>
          <button className={styles.submit} onClick={handleSubmit}>
            등록
          </button>
        </div>
      </div>
      {/* </form> */}
    </div>
  );
};

export default BuildStudyPage;

// const handleSubmit = async (e) => {
//   e.preventDefault(); // 기본 폼 제출을 막습니다.

//   // 폼 필드의 데이터를 수집합니다.
//   const formData = new FormData(e.target.closest("form"));
//   const formValues = Object.fromEntries(formData.entries());

//   // 얻은 데이터로 서버에 요청을 보냅니다.
//   try {
//     const response = await axios.post("http://localhost:8089/buildstudy", formValues); // JSON 데이터를 보내는 경우 'Content-Type': 'application/json' 헤더를 추가해야 합니다.
//     // 필요한 경우 응답을 처리합니다.
//   } catch (error) {
//     // 요청에 실패한 경우 오류를 처리합니다.
//   }
//   window.location.href = "/study";
// };

// async function handleSubmit(e) {
//   e.preventDefault();
//   const formData = new FormData(e.target.closest("form"));
//   const formValues = Object.fromEntries(formData.entries());

// try {
//   const response = await axios.post("http://localhost:8089/buildstudy?", formValues); // JSON 데이터를 보내는 경우 'Content-Type': 'application/json' 헤더를 추가해야 합니다.

//   if (response.status === 200) {
//     // 응답이 성공적으로 완료되었습니다.
//     const data = await response.json(); // 응답에 대한 데이터를 JSON 형식으로 변환합니다. (필요한 경우)
//     console.log("성공적으로 데이터가 전송되었습니다.", data);
//   } else {
//     // 응답이 실패하였습니다.
//     console.error("데이터 전송에 실패했습니다.");
//   }
// } catch (error) {
//   // 에러가 발생한 경우
//   console.error("에러가 발생했습니다.", error);
// }
// }
