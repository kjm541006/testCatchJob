import { React, useEffect, useState } from "react";
import styles from "../assets/css/PortfolioMain.module.css";
import img from "../assets/img/port_img.jpeg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faCommentDots, faHeart, faCheck } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import PortfolioModal from "../components/PortfolioModal";
import { useNavigate } from "react-router-dom";

const PortfolioMainPage = () => {
  // 아래는 react-router-dom 용 코드입니다 유지시켜주세요.
  const navigate = useNavigate();
  useEffect(() => {
    navigate("/");
  }, [navigate]);

  const dummyData = [
    {
      boardId: 1,
      bTitle: "첫 번째 게시물",
      bContents:
        "(서울=연합뉴스) 신선미 기자 = 농협은 중복을 맞아 21∼22일 농협하나로마트에서 닭고기와 삼계탕 '반값 특별할인 행사'를 연다고 20일 밝혔다.농협은 외식 물가 상승으로 인한 소비자들의 부담을 줄이고, 중복을 맞아 증가한 보양식 수요에 대응하기 위해 이번 행사를 마련했다.농협은 행사에서 목우촌 삼계탕, 닭고기 등 12품목을 50% 할인해 판매한다안병우 농협경제지주 축산경제대표이사는 복날을 맞아 대표 보양식인 삼계탕과 닭고기를 부담 없이 즐길 수 있도록 행사를 마련했다으로도 다양한 축산물 할인 판매를 통해 소비자 물가 안정에 기여하겠다고 말했다.",
      bCnt: 100,
      bLike: 20,
      bComment: 30,
      isLike: true,
      bFileName: "file1.jpg",
      bCoverFileName: img,
      bDate: "2023-07-19T15:30:00Z",
      member: {
        email: "user1@example.com",
        name: "User1",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 1,
          tagName: "java",
        },
        {
          tagId: 2,
          tagName: "git",
        },
        {
          tagId: 3,
          tagName: "gpt",
        },
      ],
      comments: [
        {
          commentId: 1,
          commentContent: "댓글1 내용입니다.",
          commentDate: "2023-07-19T16:30:00Z",
          memberName: "UserA",
          memberEmail: "userA@example.com",
        },
        {
          commentId: 2,
          commentContent: "댓글2 내용입니다.",
          commentDate: "2023-07-19T17:30:00Z",
          memberName: "UserB",
          memberEmail: "userB@example.com",
        },
      ],
    },
    {
      boardId: 2,
      bTitle: "두 번째 게시물",
      bContents: "두 번째 게시물 내용입니다.",
      bCnt: 50,
      bLike: 10,
      bComment: 40,
      isLike: false,
      bFileName: "file2.jpg",
      bCoverFileName: img,
      bDate: "2023-07-20T09:45:00Z",
      member: {
        email: "user2@example.com",
        name: "User2",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 4,
          tagName: "javascript",
        },
        {
          tagId: 5,
          tagName: "html",
        },
        {
          tagId: 6,
          tagName: "css",
        },
      ],
      comments: [
        {
          commentId: 3,
          commentContent: "댓글3 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "UserC",
          memberEmail: "userC@example.com",
        },
      ],
    },
    {
      boardId: 3,
      bTitle: "세 번째 게시물",
      bContents: "세 번째 게시물 내용입니다.",
      bCnt: 25,
      bLike: 5,
      bComment: 10,
      isLike: false,
      bFileName: "file3.jpg",
      bCoverFileName: img,
      bDate: "2023-07-21T14:20:00Z",
      member: {
        email: "user3@example.com",
        name: "User3",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 7,
          tagName: "python",
        },
        {
          tagId: 8,
          tagName: "django",
        },
        {
          tagId: 8,
          tagName: "django",
        },
      ],
      comments: [
        {
          commentId: 4,
          commentContent: "댓글4 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "Userd",
          memberEmail: "userd@example.com",
        },
      ],
    },
    {
      boardId: 1,
      bTitle: "첫 번째 게시물",
      bContents: "첫 번째 게시물 내용입니다.",
      bCnt: 100,
      bLike: 20,
      bComment: 30,
      isLike: true,
      bFileName: "file1.jpg",
      bCoverFileName: img,
      bDate: "2023-07-19T15:30:00Z",
      member: {
        email: "user1@example.com",
        name: "User1",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 1,
          tagName: "java",
        },
        {
          tagId: 2,
          tagName: "git",
        },
        {
          tagId: 3,
          tagName: "gpt",
        },
      ],
      comments: [
        {
          commentId: 1,
          commentContent: "댓글1 내용입니다.",
          commentDate: "2023-07-19T16:30:00Z",
          memberName: "UserA",
          memberEmail: "userA@example.com",
        },
        {
          commentId: 2,
          commentContent: "댓글2 내용입니다.",
          commentDate: "2023-07-19T17:30:00Z",
          memberName: "UserB",
          memberEmail: "userB@example.com",
        },
      ],
    },
    {
      boardId: 2,
      bTitle: "두 번째 게시물",
      bContents: "두 번째 게시물 내용입니다.",
      bCnt: 50,
      bLike: 10,
      bComment: 40,
      isLike: false,
      bFileName: "file2.jpg",
      bCoverFileName: img,
      bDate: "2023-07-20T09:45:00Z",
      member: {
        email: "user2@example.com",
        name: "User2",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 4,
          tagName: "javascript",
        },
        {
          tagId: 5,
          tagName: "html",
        },
        {
          tagId: 6,
          tagName: "css",
        },
      ],
      comments: [
        {
          commentId: 3,
          commentContent: "댓글3 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "UserC",
          memberEmail: "userC@example.com",
        },
      ],
    },
    {
      boardId: 3,
      bTitle: "세 번째 게시물",
      bContents: "세 번째 게시물 내용입니다.",
      bCnt: 25,
      bLike: 5,
      bComment: 10,
      isLike: false,
      bFileName: "file3.jpg",
      bCoverFileName: img,
      bDate: "2023-07-21T14:20:00Z",
      member: {
        email: "user3@example.com",
        name: "User3",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 7,
          tagName: "python",
        },
        {
          tagId: 8,
          tagName: "django",
        },
        {
          tagId: 8,
          tagName: "django",
        },
      ],
      comments: [
        {
          commentId: 4,
          commentContent: "댓글4 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "Userd",
          memberEmail: "userd@example.com",
        },
      ],
    },
    {
      boardId: 1,
      bTitle: "첫 번째 게시물",
      bContents: "첫 번째 게시물 내용입니다.",
      bCnt: 100,
      bLike: 20,
      bComment: 30,
      isLike: true,
      bFileName: "file1.jpg",
      bCoverFileName: img,
      bDate: "2023-07-19T15:30:00Z",
      member: {
        email: "user1@example.com",
        name: "User1",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 1,
          tagName: "java",
        },
        {
          tagId: 2,
          tagName: "git",
        },
        {
          tagId: 3,
          tagName: "gpt",
        },
      ],
      comments: [
        {
          commentId: 1,
          commentContent: "댓글1 내용입니다.",
          commentDate: "2023-07-19T16:30:00Z",
          memberName: "UserA",
          memberEmail: "userA@example.com",
        },
        {
          commentId: 2,
          commentContent: "댓글2 내용입니다.",
          commentDate: "2023-07-19T17:30:00Z",
          memberName: "UserB",
          memberEmail: "userB@example.com",
        },
      ],
    },
    {
      boardId: 2,
      bTitle: "두 번째 게시물",
      bContents: "두 번째 게시물 내용입니다.",
      bCnt: 50,
      bLike: 10,
      bComment: 40,
      isLike: false,
      bFileName: "file2.jpg",
      bCoverFileName: img,
      bDate: "2023-07-20T09:45:00Z",
      member: {
        email: "user2@example.com",
        name: "User2",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 4,
          tagName: "javascript",
        },
        {
          tagId: 5,
          tagName: "html",
        },
        {
          tagId: 6,
          tagName: "css",
        },
      ],
      comments: [
        {
          commentId: 3,
          commentContent: "댓글3 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "UserC",
          memberEmail: "userC@example.com",
        },
      ],
    },
    {
      boardId: 3,
      bTitle: "세 번째 게시물",
      bContents: "세 번째 게시물 내용입니다.",
      bCnt: 25,
      bLike: 5,
      bComment: 10,
      isLike: false,
      bFileName: "file3.jpg",
      bCoverFileName: img,
      bDate: "2023-07-21T14:20:00Z",
      member: {
        email: "user3@example.com",
        name: "User3",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 7,
          tagName: "python",
        },
        {
          tagId: 8,
          tagName: "django",
        },
        {
          tagId: 8,
          tagName: "django",
        },
      ],
      comments: [
        {
          commentId: 4,
          commentContent: "댓글4 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "Userd",
          memberEmail: "userd@example.com",
        },
      ],
    },
    {
      boardId: 1,
      bTitle: "첫 번째 게시물",
      bContents: "첫 번째 게시물 내용입니다.",
      bCnt: 100,
      bLike: 20,
      bComment: 30,
      isLike: true,
      bFileName: "file1.jpg",
      bCoverFileName: img,
      bDate: "2023-07-19T15:30:00Z",
      member: {
        email: "user1@example.com",
        name: "User1",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 1,
          tagName: "java",
        },
        {
          tagId: 2,
          tagName: "git",
        },
        {
          tagId: 3,
          tagName: "gpt",
        },
      ],
      comments: [
        {
          commentId: 1,
          commentContent: "댓글1 내용입니다.",
          commentDate: "2023-07-19T16:30:00Z",
          memberName: "UserA",
          memberEmail: "userA@example.com",
        },
        {
          commentId: 2,
          commentContent: "댓글2 내용입니다.",
          commentDate: "2023-07-19T17:30:00Z",
          memberName: "UserB",
          memberEmail: "userB@example.com",
        },
      ],
    },
    {
      boardId: 2,
      bTitle: "두 번째 게시물",
      bContents: "두 번째 게시물 내용입니다.",
      bCnt: 50,
      bLike: 10,
      bComment: 40,
      isLike: false,
      bFileName: "file2.jpg",
      bCoverFileName: img,
      bDate: "2023-07-20T09:45:00Z",
      member: {
        email: "user2@example.com",
        name: "User2",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 4,
          tagName: "javascript",
        },
        {
          tagId: 5,
          tagName: "html",
        },
        {
          tagId: 6,
          tagName: "css",
        },
      ],
      comments: [
        {
          commentId: 3,
          commentContent: "댓글3 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "UserC",
          memberEmail: "userC@example.com",
        },
      ],
    },
    {
      boardId: 3,
      bTitle: "세 번째 게시물",
      bContents: "세 번째 게시물 내용입니다.",
      bCnt: 25,
      bLike: 5,
      bComment: 10,
      isLike: false,
      bFileName: "file3.jpg",
      bCoverFileName: img,
      bDate: "2023-07-21T14:20:00Z",
      member: {
        email: "user3@example.com",
        name: "User3",
        mOriginalFileName: img,
      },
      tags: [
        {
          tagId: 7,
          tagName: "python",
        },
        {
          tagId: 8,
          tagName: "django",
        },
        {
          tagId: 8,
          tagName: "django",
        },
      ],
      comments: [
        {
          commentId: 4,
          commentContent: "댓글4 내용입니다.",
          commentDate: "2023-07-20T10:30:00Z",
          memberName: "Userd",
          memberEmail: "userd@example.com",
        },
      ],
    },
  ];

  const [data, setData] = useState(dummyData); // 실제할 때는 []로 바꾸기 더미데이터 지우고
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedItemId, setSelectedItemId] = useState(null);

  useEffect(() => {
    axios
      .get("서버_엔드포인트_URL")
      .then((response) => {
        setData(response.data); // 서버 응답은  { id: 2, comments: 2, views: 456, likes: 5, infoLeft: "Element 2",  "imageUrl": "이미지_URL_1",  "userImageUrl": "이미지_URL_2", }, 이런 식이여야함
        console.log(response.data);
      })
      .catch((error) => {
        console.error("데이터 가져오기 에러:", error);
      });
  }, []);

  const handleElementClick = (itemId) => {
    setSelectedItemId(itemId);
    setIsModalOpen(true);
  };

  return (
    <div className={`${styles.port_wrapper}`}>
      <div className={styles.port_page}>
        <div className={`${styles.port_sort}`}>
          <FontAwesomeIcon icon={faCheck} className={`${styles.port_checkIcon}`} />
          <span className={`${styles.port_topRated} ${styles.port_btn}`}>인기순</span>
          <FontAwesomeIcon icon={faCheck} className={`${styles.port_checkIcon} ${styles.port_invisible}`} />
          <span className={`${styles.port_new}`}>최신순</span>
        </div>
        <div className={`${styles.port_GridView}`}>
          {data.map((item) => (
            <div key={item.boardId} className={`${styles.element}`} onClick={() => handleElementClick(item.boardId)}>
              <img className={`${styles.img}`} src={item.bCoverFileName} alt="img" />
              <div className={`${styles.info}`}>
                <img className={`${styles.user_img}`} src={item.member.mOriginalFileName} alt="img" />
                <div className={`${styles.info_left}`}>{item.member.name}</div>
                <div className={`${styles.info_right}`}>
                  <FontAwesomeIcon icon={faCommentDots} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.bComment}</span>
                  <FontAwesomeIcon icon={faEye} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.bCnt}</span>
                  <FontAwesomeIcon icon={faHeart} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.bLike}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      {isModalOpen && <PortfolioModal item={data.find((item) => item.boardId === selectedItemId)} onClose={() => setIsModalOpen(false)} />}
    </div>
  );
};

export default PortfolioMainPage;
