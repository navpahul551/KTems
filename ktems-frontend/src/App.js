import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import { Route, Routes } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import './App.css';
import Cart from './components/Cart.js';
import Item from './components/Category.js';
import Home from './components/Home.js';
import Login from './components/Login.js';
import NavigationBar from './components/NavigationBar.js';
import Register from './components/Register.js';

export const baseURL = "http://localhost:4000/";

function App(props) {
  const [jwtToken, setJWTToken] = useState();
  const [tokenType, setTokenType] = useState();
  const [userDetails, setUserDetails] = useState();
  const [cookies, setCookie] = useCookies(['user']);

  function updateJWTToken(tokenDetails) {
    fetchUserDetails(tokenDetails["token"], tokenDetails["type"],
      tokenDetails["usernameOrEmail"]);

    setJWTToken(tokenDetails["token"]);
    setTokenType(tokenDetails["type"]);
    setUserDetails(userDetails);
    
    console.log("setting jwtToken and token type");
    console.log("token details:");
    console.log(tokenDetails);

    setCookie('jwtToken', tokenDetails["token"], { path: '/' });
    setCookie('tokenType', tokenDetails["type"], { path: '/' });
  }

  // fetches the user's details from the api
  // and sets the state for userDetails object
  function fetchUserDetails(jwtToken, tokenType, usernameOrEmail) {
    const requestOptions = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': tokenType + " " + jwtToken
      },
    };

    fetch(baseURL + "users/" + usernameOrEmail, requestOptions)
      .then(response => response.json())
      .then((response) => {
        if (response["errorMessage"] !== undefined) {
          toast.error(response["errorMessage"]);
        }
        else {
          setUserDetails(response);
          setCookie('userDetails', response, { path: '/', maxAge: 24 * 60 * 60 });

          // fetch cart details
          fetchCartDetails(response.id);
        }
      },
        (error) => {
          if (error["errorMessage"] !== undefined) {
            toast.error(error["errorMessage"]);
          }
          else {
            toast.error("An error occurred while fetching user data");
          }
        });
  }

  useEffect(() => {
    if (cookies.userDetails !== undefined && cookies.jwtToken !== undefined && cookies.tokenType !== undefined) {
      setUserDetails(cookies.userDetails);
      setJWTToken(cookies.jwtToken);
      setTokenType(cookies.tokenType);
    }
  }, [userDetails, jwtToken, tokenType, cookies]);

  /**
   * fetches the cart details using the user id
   * @param {int} userId 
   */
  function fetchCartDetails(userId){
    console.log("fetching cart details");
    axios({
      method: 'GET',
      url: baseURL + 'users/'+userId+'/cart'
    })
    .then(function(response){
      setCookie('cartId', response.data.id, {path: '/'});
    })
    .catch(function(error){
      console.log("error: ");
      console.log(error.response.statusText);
    });
  }

  // renders the view
  return (
    <>
        <NavigationBar jwtToken={jwtToken} username={userDetails != null ? userDetails["username"] : ""} />

        <Routes>
          <Route path="/" exact element={<Home jwtToken={jwtToken} tokenType={tokenType} />} />
          <Route path="/login" element={<Login updateJWTToken={updateJWTToken} />} />
          <Route path="/register" element={<Register updateJWTToken={updateJWTToken} />} />
          <Route path="/categories/:categoryId" element={<Item />} />
          <Route path="/cart" element={<Cart />} />
        </Routes>
      <ToastContainer />
    </>
  );
}

export default App;
