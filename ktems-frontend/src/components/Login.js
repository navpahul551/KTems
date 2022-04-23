import { MDBBtn, MDBCol, MDBContainer, MDBInput, MDBRow } from "mdbreact";
import React, { useState } from 'react';
import { Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { baseURL } from '../App.js';

const loginDivStyle = { marginTop: 50 };

const loginCardStyle = {
    marginLeft: 200,
    marginRight: 200
};

export default function Login(props) {
    const [usernameOrEmail, setUsernameOrEmail] = useState("");
    const [password, setPassword] = useState("");
    let navigate = useNavigate();

    function sendLoginRequest() {
        const requestBody = {
            usernameOrEmail: usernameOrEmail,
            password: password
        };

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        };

        fetch(baseURL + "users/login", requestOptions)
            .then(response => response.json())
            .then((response) => {
                if (response["token"] !== undefined && response["type"] !== undefined) {
                    response["usernameOrEmail"] = usernameOrEmail;
                    props.updateJWTToken(response);
                    navigate("/");
                }
                else {
                    alert("An error occurred while processing your request");
                }
            },
                (error) => {
                    if (error["errorMessage"] !== undefined) {
                        toast.error(error["errorMessage"]);
                    }
                    else{
                        toast.error("An error occurred while processing your request!");
                    }
                });
    }

    return (
        <div id="Login" style={loginDivStyle}>
            <Card style={loginCardStyle}>
                <Card.Header>
                    <p className="h1 text-center CardHeading">Login</p>
                </Card.Header>
                <Card.Body style={{ marginRight: 'auto', marginLeft: 'auto' }}>
                    <MDBContainer>
                        <MDBRow>
                            <MDBCol md="12">
                                <form>
                                    <div className="grey-text">
                                        <MDBInput
                                            label="Type your email or username"
                                            icon="user"
                                            group
                                            type="email"
                                            validate
                                            error="wrong"
                                            success="right"
                                            name="usernameOrEmail"
                                            id="usernameOrEmail"
                                            value={usernameOrEmail}
                                            onChange={(event) => {setUsernameOrEmail(event.target.value)}}
                                        />
                                        <MDBInput
                                            label="Type your password"
                                            icon="lock"
                                            group
                                            type="password"
                                            validate
                                            name="password"
                                            id="password"
                                            value={password}
                                            onChange={(event) => {setPassword(event.target.value)}}
                                        />
                                    </div>

                                    <div className="text-center">
                                        <MDBBtn onClick={sendLoginRequest}>Login</MDBBtn>
                                        &nbsp;&nbsp;&nbsp;
                                        <a href="/register">Register?</a>
                                    </div>
                                </form>
                            </MDBCol>
                        </MDBRow>
                    </MDBContainer>
                </Card.Body>
            </Card>
        </div>
    );
}
