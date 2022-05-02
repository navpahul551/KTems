import axios from 'axios';
import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Button, Card } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import { toast } from 'react-toastify';
import { baseURL } from '../App.js';
import './styles/Cart.css';

export default function Cart(props) {
    const [cookies] = useCookies();
    const isUserLoggedIn = cookies.jwtToken !== undefined && cookies.tokenType !== undefined;
    const [cartItems, setCartItems] = useState(null);
    const [cartItemsData, setCartItemsData] = useState(null);

    useEffect(() => {
        if (cartItemsData !== null) {
            // Showing the bought quantity in the select box of
            // each item in the cart here by comparing it with the 
            // actual quantity of the item in the stock left.
            cartItemsData.map((cartItem) => {
                // fetching the item details
                return axios({
                    method: 'GET',
                    url: baseURL + 'items/' + cartItem.itemId,
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': cookies.tokenType + ' ' + cookies.jwtToken
                    }
                })
                    .then(function (response) {
                        const cartItemQuantitySelect = document.getElementById('quantity_item_' + cartItem.id);
                        const itemDetailsInDatabase = response.data;

                        // filling the options of the quantity select of the item
                        for (let i = 1; i <= itemDetailsInDatabase.quantity; i++) {
                            let option = document.createElement('option');

                            option.value = i;
                            option.text = i;
                            
                            cartItemQuantitySelect.add(option);
                        }

                        if(itemDetailsInDatabase.quantity < cartItem.boughtQuantity){
                            cartItemQuantitySelect.selectedIndex = itemDetailsInDatabase.quantity - 1;
                            document.getElementById('alert-item-' + cartItem.id).hidden = false;

                            console.log("patching the item's quantity   ...");
                            // send an API request to update the bought quantity of the item
                            axios({
                                method: 'PATCH',
                                url: baseURL + 'cartItems/' + cartItem.id,
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': cookies.tokenType + ' ' + cookies.jwtToken
                                },
                                params: {
                                    quantity: itemDetailsInDatabase.quantity
                                }
                            })
                            .then(function(response){
                                console.log("response: ");
                                console.log(response);
                            })
                            .catch(function(error){
                                console.log("error: ");
                                console.log(error);
                                if(error.response !== undefined){
                                    console.log(error.response);
                                }
                            });
                        }
                        else{
                            cartItemQuantitySelect.selectedIndex = cartItem.boughtQuantity-1;
                        }
                    })
                    .catch(function (error) {
                        console.log("an error occurred while setting item quantity");
                        console.log(error);
                        // console.log(error.response.data);
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
                setCartItems(response.data.cartItemsDetails.map((cartItem, index) => {
                    // fetch the item's details
                    let quantityNumberOptions = [];

                    for (let i = 1; i <= 50; i++) {
                        quantityNumberOptions.push(<option key={"item_" + cartItem.id + "_option_" + i} value={i}>{i}</option>);
                    }

                    return (<Card key={"item_" + cartItem.id} id={"item_" + cartItem.id}>
                        <Card.Body>
                            <Card.Img variant="right" alt={cartItem.itemDetails.name} src="/logo192.png" style={{ float: 'right' }} />
                            <Card.Title>Item: {cartItem.itemDetails.name}</Card.Title>
                            <Card.Text>Description: {cartItem.itemDetails.description}</Card.Text>
                            <Card.Text>Price: ${cartItem.itemDetails.price}</Card.Text>
                            Quantity: <select data-quantity-bought={cartItem.quantity} id={"quantity_item_" + cartItem.id}></select><br /><br />
                            <Button variant="danger" id={"cart_item_delete_" + cartItem.id} onClick={handleCartItemDelete}>Delete</Button>
                            <br />
                            <Alert key={"alert-item-" + cartItem.id} id={"alert-item-" + cartItem.id} variant="warning" hidden>
                                The item's quantity has been reduced because their weren't enough items in the stock.
                            </Alert>
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