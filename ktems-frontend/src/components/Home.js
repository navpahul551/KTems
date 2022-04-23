import "@fontsource/baloo-2";
import axios from 'axios';
import React, { useState } from 'react';
import { Button, Card } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { baseURL } from "../App";

const homeStyle = {
    marginTop: 50,
    textAlign: 'center',
    alignItems: 'center',
    float: 'center'
};

const h1Style = {
    marginTop: 15,
    textAlign: 'center',
    fontFamily: "Baloo 2, cursive"
};

const cardStyle = {
    marginLeft: 'auto',
    marginRight: 'auto',
    borderRadius: 6,
    backgroundColor: 'aquamarine'
};

const rowStyle = {
    marginRight: 20,
    marginLeft: 20
}

export default function Home(props) {
    const [cookies] = useCookies();
    const [categoryButtons, setCategoryButtons] = useState();
    let navigate = useNavigate();

    const showCategoryItems = ({target}) => {
        const categoryId = target.id.charAt(target.id.length-1); 
        navigate("/categories/" + categoryId);
    };

    // if the user is logged in then fetch the categories data
    if(cookies.jwtToken !== undefined && cookies.tokenType !== undefined){
        axios({
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': cookies.tokenType + " " + cookies.jwtToken    
            },
            url: baseURL + "categories"
        })
        .then(function(response){
            setCategoryButtons(response.data.map((item, index) => {
                return <Button variant="primary" key={"category_" + item.id} id={"category_" + item.id} onClick={showCategoryItems}>{item["name"]}</Button>
              }));
        })
        .catch(function(error){
            toast.error(error.response.statusText["errorMessage"]);
        });
      }

  /**
   * Opens the items page for the specific category
   */
//   const showCategoryItems = useCallback(({ target }) => {
//     const categoryId = target.id.charAt(target.id.length - 1);

//     let categoryName = "";

//     cookies.categories.forEach((category) => {
//       if(category.id === parseInt(categoryId)){
//         const nameAsList = category.name.split(' ');
//         categoryName = nameAsList.join('-');
//         return;
//       }
//     });

//     const requestOptions = {
//       method: 'GET',
//       headers: {
//         'Content-Type': 'application/json',
//         'Authorization': tokenType + " " + jwtToken
//       }
//     };

//     // fetching items data
//     fetch(baseURL + "categories/" + categoryId + "/items", requestOptions)
//       .then(response => response.json())
//       .then((response) => {
//         if (response["errorMessage"] !== undefined) {
//           toast.error(response["errorMessage"]);
//         }
//         else {
//           setItemsData(response);
//           setCookie('itemsData', response, { path: "/", maxAge: 24 * 60 * 60 });
//           // window.location = "/items";
//           navigate("/categories/" + categoryName);
//         }
//       },
//         (error) => {
//           if (error["errorMessage"] !== undefined) {
//             toast.error(error["errorMessage"]);
//           }
//           else {
//             toast.error("An error occurred while processing your request!");
//           }
//         });

//   }, [cookies.categories, tokenType, jwtToken, setCookie, navigate]);

    return (
        <div style={homeStyle}>
            <div className="row" style={rowStyle}>
                <div className="col-md-6">
                    <Card style={cardStyle}>
                        <Card.Body>
                            <Card.Title style={h1Style}>CATEGORIES</Card.Title>
                            <Card.Text>
                                Select a category from below to show its items.
                            </Card.Text>
                            {categoryButtons !== undefined ? categoryButtons : ""}
                        </Card.Body>
                    </Card>
                </div>

                <div className="col-md-6">
                    <Card style={cardStyle}>
                        <Card.Body>
                            <Card.Title style={h1Style}>Trending items</Card.Title>
                            <Card.Text>
                                Shows the mostly purchased items
                            </Card.Text>

                        </Card.Body>
                    </Card>
                </div>
            </div>

        </div>
    );
}