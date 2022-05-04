import axios from 'axios';
import React, { useCallback, useEffect, useState } from 'react';
import { Button, Card } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import ReactDOM from 'react-dom';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { baseURL } from '../App';
import Loader from './Loader.js';

export default function Item() {
    const [cookies] = useCookies();
    const { categoryId } = useParams();
    const [itemsData, setItemsData] = useState();
    const [items, setItems] = useState();
    const [categoryDetails, setCategoryDetails] = useState();
    const [loading, setLoading] = useState(true);

    const isUserLoggedIn = (cookies.jwtToken !== undefined && cookies.tokenType !== undefined);

    const handleAddToCart = useCallback(({ target }) => {
        const itemCard = ReactDOM.findDOMNode(ReactDOM.findDOMNode(target).parentNode).parentNode;
        const itemId = parseInt(itemCard.id.charAt(itemCard.id.length - 1));
        const quantitySelect = document.getElementById('quantity_item_' + itemId);

        // add item to cart on the server
        axios({
            method: 'POST',
            url: baseURL + 'carts/' + cookies.cartId + '/addItems',
            data: {
                cartId: cookies.cartId,
                itemId: itemId,
                boughtQuantity: quantitySelect.value
            }
        })
            .then(function (response) {
                toast.success(response.data);
            })
            .catch(function (error) {
                console.log(error);
                console.log(error.response);
                toast.error(error.response.data);
            })
            .then(function () {
                console.log("complete function executed");
            });
    }, [cookies.cartId]);

    const handleBuyNow = useCallback(({ target }) => {
        console.log("Executing buy now method");
        console.log(target);
    }, []);

    useEffect(() => {
        if (isUserLoggedIn) {
            const itemsDataAbortController = new AbortController();
            const categoryDetailsAbortController = new AbortController();
            // fetching items data
            axios({
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': cookies.tokenType + " " + cookies.jwtToken
                },
                url: baseURL + "categories/" + categoryId + "/items",
                signal: itemsDataAbortController.signal
            })
                .then(function (response) {
                    setItemsData(response.data);

                    setItems(response.data.map((item, index) => {
                        let quantityNumberOptions = [];

                        for (let i = 1; i <= parseInt(item.quantity); i++) {
                            quantityNumberOptions.push(<option key={"item_" + item.id + "_option_" + i} value={i}>{i}</option>);
                        }

                        return (<Card key={"item_" + item.id} id={"item_" + item.id}>
                            <Card.Body>
                                <Card.Img variant="right" alt={item.name} src="/logo192.png" style={{ float: 'right' }} />
                                <Card.Title>Item: {item.name}</Card.Title>
                                <Card.Text>Description: {item.description}</Card.Text>
                                <Card.Text id={"item_price_" + item.id} data-item-price={item.price}>Price: ${item.price}</Card.Text>
                                Quantity: <select id={"quantity_item_" + item.id}>{quantityNumberOptions}</select><br /><br />
                                <Button variant="warning" onClick={handleAddToCart}>Add to cart</Button>
                                <Button variant="primary" onClick={handleBuyNow}>Buy Now</Button>
                            </Card.Body>
                        </Card>);
                    }));

                    setLoading(false);
                })
                .catch(function (error) {
                    if (error.name === 'AbortError') return;
                    console.log(error);
                    toast.error(error.response.statusText);
                });

            // fetching category data
            axios({
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': cookies.tokenType + " " + cookies.jwtToken
                },
                url: baseURL + "categories/" + categoryId,
                signal: categoryDetailsAbortController.signal
            })
                .then(function (response) {
                    setCategoryDetails(response.data);
                })
                .catch(function (error) {
                    if (error.name === 'AbortError') return;
                    console.log(error);
                    toast.error(error.response.statusText);
                });

            return (() => {
                itemsDataAbortController.abort();
                categoryDetailsAbortController.abort();
            });
        }

    }, [categoryId, cookies.jwtToken, cookies.tokenType, handleAddToCart, handleBuyNow, isUserLoggedIn]);

    if (!isUserLoggedIn) {
        return (<h1 style={{ textAlign: 'center', marginTop: 50, backgroundColor: 'aquamarine' }}>
            Unable to fetch item data.
        </h1>);
    }

    if (loading === true) {
        return <Loader />
    }
    else {
        return (
            <div className="container" style={{ marginTop: 10 }}>
                <div className="row justify-content-center" style={{ fontSize: 50, backgroundColor: 'aquamarine' }}>{categoryDetails !== undefined && categoryDetails !== null ? categoryDetails.name.toUpperCase() : "Unable to get category details"}</div>
                <div className="row">{items}</div>
            </div>
        );
    }

}