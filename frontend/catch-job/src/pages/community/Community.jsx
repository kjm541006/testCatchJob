function Card(props) {
  return (
    <div className="communityCard">
      <div className="userSection">
        <div>유저프로필</div>
        <div>유저닉네임</div>
      </div>
      <div className="cardContents">
        <div className="cardContentsComponents">
          <div className="cardContentsCompnents">
            <div className="cardContentsComponents_Title">
              <h3>글제목</h3>
            </div>
            <div className="cardContentsComponents_TextArea">
              <p>글내용</p>
            </div>
            <div className="cardContentsComponents_morebutton">
              <button>펼치기</button>
            </div>
            <div className="cardContentsCOmponents_bottom">
              <button>
                <img src="" alt="" />
              </button>
              <button>
                <span>댓글</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
