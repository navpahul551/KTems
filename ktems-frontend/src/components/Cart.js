import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Card } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import { toast } from 'react-toastify';
import { baseURL } from '../App.js';

export default function Cart(props) {
    const [cookies] = useCookies();
    const isUserLoggedIn = cookies.jwtToken !== undefined && cookies.tokenType !== undefined;
    const [cartItems, setCartItems] = useState(null);
    const [cartItemsData, setCartItemsData] = useState(null);

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

    useEffect(() => {
        // fetch cart details when the component mounts
        if (isUserLoggedIn && cookies.cartId !== undefined) {
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
                                <Button variant="danger">Delete</Button>
                            </Card.Body>
                        </Card>);
                    }));
                })
                .catch(function (error) {
                    toast.error(error.response.statusText);
                });
        }
    }, [cookies.cartId, cookies.jwtToken, cookies.tokenType, isUserLoggedIn]);


    return (<div className="container" style={{ marginTop: 10 }}>
        <div className="row justify-content-center" style={{ fontSize: 50, backgroundColor: 'aquamarine' }}>CART</div>
        <div className="row">{cartItems}</div>
    </div>);
}