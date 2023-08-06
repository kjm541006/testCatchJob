import axios from "axios";
import { useEffect, useState } from "react";

const CommunityComment = (props) => {
  const [comments, setComments] = useState([]);
  const [commentVal, setCommentVal] = useState();

  const getComments = async () => {
    try {
      await axios
        .get("http://43.202.98.45:8089/community/comment/list", {
        // .get("http://localhost:8089/community/comment/list", {
          params: {
            communityId: props.communityId,
          },
        })
        .then((response) => {
          console.log(response.data);
          setComments(response.data);
        });
    } catch (error) {
      console.error(error);
    }
  };

  const handleSubmit = async () => {
    if (commentVal.trim() === "") {
      alert("댓글을 입력해주세요.");

      return;
    }

    const newComment = {
      communityId: props.communityId,
      cComcontent: commentVal,
      //email: userEmail,
    };
    try {
      await axios.post(`http://43.202.98.45:8089/community/comment/insert`, newComment).then((response) => {
      // await axios.post(`http://localhost:8089/community/comment/insert`, newComment).then((response) => {
        console.log(response.data);

        setCommentVal("");
        getComments();
      });
    } catch (error) {
      console.error("Error submitting comment", error);
    }
  };

  useEffect(() => {
    if (props.open) getComments();
  }, [props.open]);

  const handleCommentChange = (event, i) => {
    setCommentVal(event.target.value);
  };

  const handleEditComment = (commentId) => {
    // 수정 기능을 구현하는 로직을 작성합니다.
    // commentId를 기반으로 해당 댓글을 수정하는 작업을 수행합니다.
  };

  const handleDeleteComment = (commentId) => {
    // 삭제 기능을 구현하는 로직을 작성합니다.
    // commentId를 기반으로 해당 댓글을 삭제하는 작업을 수행합니다.
  };
  return (
    <>
      <div className="modal">
        <div className="mentment">
          <textarea
            className="mentmentment"
            maxLength={1000}
            placeholder="댓글을 입력해주세요."
            style={{ height: 20 }}
            value={commentVal}
            onChange={handleCommentChange}
          />
        </div>
      </div>
      <div style={{ display: "flex", justifyContent: "flex-end" }}>
        <button
          style={{
            fontFamily: "Inter",
            fontWeight: "700",
            fontSize: "11px",
            color: "white",
            backgroundColor: "#555",
            border: 0,
            borderRadius: "4px",
          }}
          onClick={handleSubmit}
        >
          댓글 등록
        </button>
      </div>

      <div className="commentsContainer">
        {comments &&
          comments.map((comment) => (
            <div key={comment.communityId} className="commentment">
              <div className="commentmentuser">
                <div>
                  <img src={comment.profileImg} alt="프로필" />
                  {comment.memberId}
                </div>
                <div className="commenteditBtn">
                  <span style={{ color: "#77BC1F" }} onClick={() => handleEditComment(comment.communityId)}>
                    수정
                  </span>
                  <span style={{ color: "#E2432E" }} onClick={() => handleDeleteComment(comment.communityId)}>
                    삭제
                  </span>
                </div>
              </div>

              <div className="commentmentment">
                <p>{comment.ccomcontent}</p>
              </div>
            </div>
          ))}
      </div>
    </>
  );
};

export default CommunityComment;
