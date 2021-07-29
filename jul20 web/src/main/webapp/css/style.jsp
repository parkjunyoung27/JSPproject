<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    /*기본 배경*/
*{
	margin:0;
	padding:0;
}

body{
background-color: #A2E9FF;
margin: 0;
padding: 0;
}

#mainWrapper{
	margin: 0 auto;
	width: 1100px;
	background-color: #AECDFF;
	margin-left: 30%;
	transform: translate(-30%);	
	border-radius: 10px;
	height: auto;
	overflow: hidden;
	min-height: 700px;
}

#board{
	width:900px;
	float:right;
	min-height: 600px;
	margin-top:30px;
	margin-right: 10px;
	border: 2px white solid;
	background-color: #B8D7FF;
	border-radius: 10px;
	margin-bottom: 30px;
}

#navbar{
	height: 60px;
	line-height: 60px;
	padding-left: 60px;
	padding-right: 60px;
	background-color: #3C5087;
	margin-bottom: 30px;
}

#navbar a{
	text-decoration: none;
	font-size: 25px;
	font-weight: bold;
	color: white;
}

#navbar a img{
	width: 55px;
	vertical-align: middle;
}

#navmenu{
	float: right;
	list-style: none;
	margin: 0;
	padding: 0;
	line-height: 60px;
}

#navmenu li{
	float: left;
	margin-left: 60px;
	line-height: inherit;
}

#navbar #navmenu li a{
	color: white;
	font-size: 17px;
	font-weight: bold;
	text-decoration: none;
}

#liSearchOption{
clear:both;
clear: both;
text-align: center;
margin-top: 20px;
}

#liSearchOption > div{
	margin: 0 auot;
	margin-top: 30px;
	width: auto;
	height: 100px;
}

#logo_img{
	text-align:center
}

#detail_link{
	text-decoration: none;
	color: black;
}

/* 게시판 */

.title{
	font-size: 150%;
	font-weight: bold;
	margin-top: 15px;
	margin-bottom: 15px;
}

ul, li{
	padding: 0;
	margin: 0;
	list-style: none;
	text-align: center;
}

li{
	height:35px;
	line-height: 35px;
}

#ulTable{
	margin-top: 10px;
}

#ulTable > li:first-child > ul > li{
	background-color: #0064CD;
	font-weight: bold;
	text-align: center;
	color: white;
}

#ulTable > li > ul{
	clear: both;
	padding: 0 auto;
	position: relative;
	min-width: 40px;
}

#ulTable > li > ul > li{
	float: left;
	font-size: 10pt;
	border-bottom: 1px solid silver;
}

#ulTable > li > ul > li:first-child{ /* 첫번째 칸 */
	width: 10%;
}
#ulTable > li > ul > li:first-child+li{
	width: 45%;
}
#ulTable > li > ul > li:first-child+li+li{
	width: 20%;
}
#ulTable > li > ul > li:first-child+li+li+li{
	width: 15%;
}
#ulTable > li > ul > li:first-child+li+li+li+li{
	width: 10%;
}

#menu_title:hover{font-size: 150%;}
#divPaging div:hover{font-size: 150%; font-weight: bold; cursor: pointer;}
#freewrite:hover{font-size: 150%; font-weight: bold;}

/*로그인 폼*/

#login-form{
	width:300px;
	float:left;
	margin-left: 300px;
	margin-top: 200px;
	border: none;
	padding:20px;
	border-radius: 5px;
	background-color:#EEEFF1;
}

.text-field{
	width: 280px;
	border: none;
	border-radius: 5px;
	font-size: 14px;
	margin-bottom: 10px;
	padding: 10px;
}

.submit-btn{
	width: 100%;
	border: none;
	background-color: #0064CD;
	border-radius: 5px;
	font-size: 14px;
	color: white;
	margin-bottom: 30px;
	padding: 10px;
}

.links{
	width: 100%;
	text-align: center;
}

.links a {
	font-size: 15px;
	color: #9B9B98;
}

/*상세보기*/
#dtitle{
	height: 80px;
	width:100%;
	text-align: center;
	line-height: 80px;
	border: 1px white solid;
	font-size: 130%;
	font-weight: bold;
}


#dmember{
	margin-left: 20px;
	width: 50%;
	float:left;
	height: 50px;
	text-align: left;
	line-height: 50px;	
}

#ddate{
	margin: 0;
	width: 45%;
	float:left;
	height: 50px;
	line-height: 50px;
	text-align: right;
}

#dcontent{
	float:left;
	width: 880px;
	padding: 10px;
	min-height: 200px;	
	border: 1px white solid;	
}

#dcontent img {
    width: 300px;
}

#commentInput{
	width: 496px;
	height: 120px;
	background-color: gray;
}

#commentInput textarea{
	margin: 0;
	padding: 10px;
	width: 480px;
	height: 90px;
	border: 0;
}

#commentInput button{
	margin:0;
	padding:0;
	height: 30px;
	width: 500px;
	background-color: #0064CD;
	color: white;
}

#bbutton{
	text-align: center;
	width: 900px;
	float: right;
	margin-top: 30px;
	margin-bottom: 50px;
}

#comment{
	width: 880px;
	float: left;
	min-height: 200px;
	padding: 10px;
}

.commentWrite:hover{cursor:pointer;}

#Paging{
	text-align: center;	
	margin-top: 20px;
}

#Paging a{
	text-decoration: none;
	color: black;
	margin: 5px;
}

footer{
	background-color: #3C5087;
	clear: both;
}

#footer{
	text-align: center;
	margin-top: 70px;
    padding: 10px;
}

#footer p {
	margin-top: 5px;
	color: white;
}

    