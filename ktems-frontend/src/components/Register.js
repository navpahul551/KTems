import { MDBBtn, MDBCol, MDBContainer, MDBInput, MDBRow } from "mdbreact";
import React, { Component } from 'react';
import { Card } from 'react-bootstrap';
import { toast } from "react-toastify";
import '../App.css';
import { baseURL } from '../App.js';

const loginDivStyle = { marginTop: 50 };

const loginCardStyle = {
    marginLeft: 200,
    marginRight: 200
};

export default class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            email: "",
            firstName: "",
            lastName: ""
        };
        this.sendRegisterRequest = this.sendRegisterRequest.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    sendRegisterRequest() {
        const requestBody = {
            username: this.state.username,
            password: this.state.password,
            email: this.state.email,
            firstName: this.state.firstName,
            lastName: this.state.lastName
        };

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        };

        fetch(baseURL + "users/register", requestOptions)
            .then(response => response.json())
            .then((response) => {
                if (response["token"] !== undefined && response["type"] !== undefined) {
                    this.props.updateJWTToken(response);
                }
                else {
                    toast.error("An error occurred while processing your request!!!");
                    // alert("An error occurred while processing your request");
                }
            },
                (error) => {
                    if (error["errorMessage"] !== undefined) {
                        // alert(error["errorMessage"]);
                        toast.error(error["errorMessage"]);
                    }
                    else{
                        toast.error("An error occurred while processing your request!!!");
                    }
                });
    }

    handleInputChange(event) {
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }

    render() {
        return (
            <div id="Login" style={loginDivStyle}>
                <Card style={loginCardStyle}>
                    <Card.Header>
                        <p className="h1 text-center CardHeading">Register</p>
                    </Card.Header>
                    <Card.Body style={{ marginRight: 'auto', marginLeft: 'auto' }}>
                        <MDBContainer>
                            <MDBRow>
                                <MDBCol md="12">
                                    <form>
                                        <div className="grey-text">
                                            <MDBInput
                                                label="Type your username"
                                                icon="user"
                                                group
                                                type="text"
                                                validate
                                                error="wrong"
                                                success="right"
                                                name="username"
                                                id="username"
                                                value={this.state.username}
                                                onChange={this.handleInputChange}
                                            />
                                            <MDBInput
                                                label="Type your password"
                                                icon="lock"
                                                group
                                                type="password"
                                                validate
                                                name="password"
                                                id="password"
                                                value={this.state.password}
                                                onChange={this.handleInputChange}
                                            />
                                            <MDBInput
                                                label="Type your email"
                                                icon="envelope"
                                                group
                                                type="email"
                                                validate
                                                name="email"
                                                id="email"
                                                value={this.state.email}
                                                onChange={this.handleInputChange}
                                            />
                                            <MDBInput
                                                label="Type your first name"
                                                icon="user"
                                                group
                                                type="text"
                                                validate
                                                name="firstName"
                                                id="firstName"
                                                value={this.state.firstName}
                                                onChange={this.handleInputChange}
                                            />
                                            <MDBInput
                                                label="Type your last name"
                                                icon="user"
                                                group
                                                type="text"
                                                validate
                                                name="lastName"
                                                id="lastName"
                                                value={this.state.lastName}
                                                onChange={this.handleInputChange}
                                            />
                                        </div>

                                        <div className="text-center">
                                            <MDBBtn onClick={this.sendRegisterRequest}>Register</MDBBtn>
                                            &nbsp;&nbsp;&nbsp;
                                            <a href="/login">Login?</a>
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
}
