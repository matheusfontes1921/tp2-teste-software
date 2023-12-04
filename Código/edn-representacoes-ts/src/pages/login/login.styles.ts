import styled from "styled-components"
export const Container = styled.div`
background-color: #483D8B;
top: 0;
left:0;
bottom:0;
right:0;
height:100vh;
display: flex;
align-items: center;
justify-content: center;
width:100%;
`
export const Card = styled.div`
background-color: white;
width: 30%;
height: 70%;
border-radius: 4px;
display: flex;
flex-direction: column;
align-items: center;
`

export const CardElements = styled.div`
display: flex;
flex-direction: column;
padding: 14%;
width: 60%;
`
export const CardLogin = styled.h1`
padding-top:5%;
font-size: 4vh;
font-family: "Poppins";
`

export const CardButtons = styled.div`
display: flex;
flex-direction: column;
padding-top: 2%;
`

export const CardButtonSignUp = styled.div`
display: flex;
justify-content: space-between;
padding-bottom: 2vh;
width: 100%;
`

export const ForgotButton = styled.div`
padding: 0 1vh 0 0;
`