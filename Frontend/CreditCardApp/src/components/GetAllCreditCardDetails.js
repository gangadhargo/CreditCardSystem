import React, { useEffect, useState } from "react";
import * as ReactBootStrap from "react-bootstrap";
import axios from "axios";

const GetAllCreditCardDetails = () => {
  const [creditCards, setCards] = useState({ cards: [] });

  useEffect(() => {
    const fetchAllCardsData = async () => {
      const { data } = await axios(
        "/allCreditCards"
      );
      console.log(data.data);
      setCards({ cards: data.data });
     
    };
    fetchAllCardsData();
  }, [setCards]);

  return (
    <div>
      <ReactBootStrap.Table striped bordered hover>
        <thead>
          <tr>
            <th>Name</th>
            <th>Card Number</th>
            <th>Balance</th>
            <th>Limit</th>
          </tr>
        </thead>
        <tbody>
          {creditCards.cards &&
            creditCards.cards.map((item) => (
              <tr key={item.id}>
                <td>{item.name}</td>
                <td>{Array.from(item.creditCardNumber.toString().replaceAll(/\D/g, '').matchAll(/(\d{0,4})(\d{0,4})(\d{0,4})(\d{0,4})/g))[0].slice(1, 5).join(' ').trim()}</td>
                <td>{item.balance < 0 ? <p style={{color:"red"}}>£{item.balance}</p> : '£'+item.balance}</td>
                <td>{'£'+item.limit}</td>
              </tr>
            ))}
        </tbody>
      </ReactBootStrap.Table>
    </div>
  );
};

export default GetAllCreditCardDetails;
