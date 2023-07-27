import React, {useState} from 'react';
import { HtmlEditor, Image, Inject, Link, QuickToolbar, RichTextEditorComponent, Toolbar } from '@syncfusion/ej2-react-richtexteditor';
import styles from '../assets/css/BuildPortfolio.module.css';
import '@syncfusion/ej2-base/styles/material.css';
import '@syncfusion/ej2-buttons/styles/material.css';
import '@syncfusion/ej2-inputs/styles/material.css';
import '@syncfusion/ej2-navigations/styles/material.css';
import '@syncfusion/ej2-popups/styles/material.css';
import '@syncfusion/ej2-splitbuttons/styles/material.css';
import '@syncfusion/ej2-react-richtexteditor/styles/material.css';
import "@syncfusion/ej2-icons/styles/material.css";
import { enableRipple } from '@syncfusion/ej2-base';
enableRipple(true);

const BuildPortfolioPage = () => {

  const [title, setTitle] = useState('');

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  }

  return(
  <div className={`${styles.wrapper}`}>
   <input type="text"
          tabIndex="1"
          name="email"
          value={title}
          onChange={handleTitleChange}
          placeholder="제목을 입력하세요" 
          className={`${styles.realEditorTitle}`}></input>
    <div className={`${styles.realEditor}`}  tabIndex="2">
      <RichTextEditorComponent height="800px" >
        <Inject services={[HtmlEditor, Toolbar, Image, Link, QuickToolbar]} />
      </RichTextEditorComponent>
    </div>
  </div>
  );
};
export default BuildPortfolioPage;

