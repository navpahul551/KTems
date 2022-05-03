import axios from 'axios';
import React from 'react';
import { Container, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import { FaShoppingCart } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { baseURL } from '../App.js';
import logo from '../ktems-logo.png';
import './styles/NavigationBar.css';

export default function NavigationBar(props) {
    const [cookies, setCookie, removeCookie] = useCookies();

    const loginNavLink = <Nav.Link className="navItem" href="/login">Login</Nav.Link>;
    const registerNavLink = <Nav.Link className="navItem" href="/register">Register</Nav.Link>;

    const userLoggedIn = props.jwtToken != null;
    const navigate = useNavigate();

    // clearing cookie data to logout the user
    const logout = () => {
        console.log("logout...");
        
        // logging out the user from the security context at the API
        axios({
            method: 'POST',
            url: baseURL + 'users/logout',
            headers: {
                'Authorization': cookies.tokenType + ' ' + cookies.jtwToken
            }
        })
        .then(function(response){
            console.log("success:");
            console.log(response);
        })
        .catch(function(error){
            console.log("error: ");
            console.log(error);
        });
        
        // removing the saved cookies
        removeCookie('jwtToken');
        removeCookie('tokenType');
        removeCookie('userDetails');

        // redirecting to the home page
        if(window.location.pathname === "/"){
            window.location.reload();
        }
        else{
            navigate("/");
        }
    };

    const dropDownMenu = (<NavDropdown className="navItem" title={"Hi, " + props.username} id="navbarScrollingDropdown">
        <NavDropdown.Item href="profile">My profile</NavDropdown.Item>
        <NavDropdown.Item href="orders">My orders</NavDropdown.Item>
        <NavDropdown.Divider />
        <NavDropdown.Item href="#" onClick={logout}>
            Logout
        </NavDropdown.Item>
    </NavDropdown>);

    const logoStyle = {
        display: 'inline-flex',
        height: 73,
        width: 236
    };

    return (
        <>
            <Navbar collapseOnSelect fixed='top' expand='sm' className="sticky-nav">
                <Container>
                    <Navbar.Toggle aria-controls='responsive-navbar-nav' />
                    <Navbar.Collapse id='responsive-navbar-nav'>
                        <img src={logo} alt="logo" style={logoStyle} />
                        <Nav className="ms-auto">
                            <Nav.Link className="navItem" href="/">Home</Nav.Link>
                            {userLoggedIn ? dropDownMenu : loginNavLink}
                            {userLoggedIn ? "" : registerNavLink}
                            {userLoggedIn ? <Nav.Link className="navItem" href="/cart"><FaShoppingCart/></Nav.Link> : ""}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}
