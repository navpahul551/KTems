import axios from 'axios';
import { MDBBtn } from 'mdbreact';
import React, { useCallback, useEffect, useState } from 'react';
import { Button, Card } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import { toast } from 'react-toastify';
import { baseURL } from '../App.js';
import './styles/Cart.css';

export default function Cart(props) {
    const [cookies] = useCookies();
    const isUserLoggedIn = cookies.jwtToken !== undefined && cookies.tokenType !== undefined;
    const [cartItems, setCartItems] = useState(null);
    const [cartItemsData, setCartItemsData] = useState(null);

    const quantityDecrementHandler = ({target}) => {
        const inputElement = target.nextElementSibling;
        const quantity = inputElement.value;

        // axios({
        //     method: 'PATCH',
        //     url: baseURL + 
        // })
    };

    const quantityIncrementHandler = ({target}) => {
        const inputElement = target.previousElementSibling;
        const quantity = inputElement.value;
        
    };

    useEffect(() => {
        if (cartItemsData !== null) {
            cartItemsData.map((item) => {
                return axios({
                    method: 'GET',
                    url: baseURL + 'items/' + item.itemId,
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': cookies.tokenType + ' ' + cookies.jwtToken
                    }
                })
                    .then(function (response) {
                        const cartItemQuantitySelect = document.getElementById('quantity_item_' + item.id);

                        for (let i = 1; i <= response.data.quantity; i++) {
                            let option = document.createElement('option');

                            option.value = i;
                            option.text = i;
                            if (item.quantity === i) option.selected = true;

                            cartItemQuantitySelect.add(option);
                        }
                    })
                    .catch(function (error) {
                        console.log("an error occurred while setting item quantity");
                        console.log(error.response.data);
                    });
            });
        }
    }, [cartItemsData, cookies.jwtToken, cookies.tokenType]);

    const fetchCartItemsData = useCallback(() => {
        const handleCartItemDelete = ({target}) => {
            console.log(target);
            const cartItemId = target.id.split('_')[3];

            axios({
                method: 'DELETE',
                url: baseURL + 'cartItems/' + cartItemId,
                headers: {
                    'Content-Type': 'application/json', 
                    'Authorization': cookies.tokenType + ' ' + cookies.jwtToken
                }
            })
            .then(function(response){
                toast.success(response.data);
                fetchCartItemsData();
            })
            .catch(function(error){
                toast.error(error.response.statusText);
            });
        };

        axios({
            method: 'GET',
            url: baseURL + 'carts/' + cookies.cartId,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': cookies.tokenType + ' ' + cookies.jwtToken
            }
        })
            .then(function (response) {
                // setting cart items to show on the UI
                setCartItemsData(response.data.cartItemsDetails);
                setCartItems(response.data.cartItemsDetails.map((item, index) => {
                    // fetch the item's details
                    let quantityNumberOptions = [];

                    for (let i = 1; i <= 50; i++) {
                        quantityNumberOptions.push(<option key={"item_" + item.id + "_option_" + i} value={i}>{i}</option>);
                    }

                    return (<Card key={"item_" + item.id} id={"item_" + item.id}>
                        <Card.Body>
                            <Card.Img variant="right" alt={item.name} src="/logo192.png" style={{ float: 'right' }} />
                            <Card.Title>Item: {item.name}</Card.Title>
                            <Card.Text>Description: {item.description}</Card.Text>
                            <Card.Text>Price: ${item.buyingPrice}</Card.Text>
                            Quantity: <select defaultValue={item.quantity} id={"quantity_item_" + item.id}></select><br /><br />
                            <Card.Text>
                                <MDBBtn  color="danger" size="sm" onClick={quantityDecrementHandler}><strong>-</strong></MDBBtn>
                                <input defaultValue={item.quantity} type="text" className="cart-quantity-input" style={{height: 44, width: 52, marginLeft: -6, marginRight: -6, border: 'none', textAlign: 'center'}} readOnly={true}/>
                                <MDBBtn  color="primary" size="sm" onClick={quantityIncrementHandler}>+</MDBBtn>
                            </Card.Text>
                            <Button variant="danger" id={"cart_item_delete_" + item.id} onClick={handleCartItemDelete}>Delete</Button>
                        </Card.Body>
                    </Card>);
                }));
            })
            .catch(function (error) {
                toast.error(error.response.statusText);
            });
    }, [cookies.cartId, cookies.jwtToken, cookies.tokenType]);

    useEffect(() => {
        // fetch cart details when the component mounts
        if (isUserLoggedIn && cookies.cartId !== undefined) {
            fetchCartItemsData();       
        }
    }, [cookies.cartId, fetchCartItemsData, isUserLoggedIn]);

    return (<div className="container" style={{ marginTop: 10 }}>
        <div className="row justify-content-center" style={{ fontSize: 50, backgroundColor: 'aquamarine' }}>CART</div>
        <div className="row">{cartItems === null || cartItems === undefined || cartItems.length === 0 ? 'The cart is empty' : cartItems}</div>
    </div>);
}