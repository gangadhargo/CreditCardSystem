import { useState, useEffect } from "react";
import axios from "axios";

const AddCreditCard = () =>{
    const initialValues = { name: "", cardNumber: "", limit: "" };
  const [formValues, setFormValues] = useState(initialValues);
  const [formMessages, setFormMessages] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormMessages(validate(formValues));
    setIsSubmit(true);
  };
  
  useEffect(() => {
    const { name, cardNumber, limit, balance } = {...formValues};
    if (Object.keys(formMessages).length === 0 && isSubmit) {
    const addCreditCard = async () => {
       await axios.post(
          "/addCreditCard", {"name": name, "creditCardNumber": cardNumber, "limit" : limit}
        ).then((response) => {
            if(response.data.type === 'ERROR'){
                formMessages.errorMessage = response.data.message;
            }else if(response.data.type === 'SUCCESS'){
                formMessages.successMessage = response.data.message;
            }
            setFormValues(initialValues);
        });
       
      };
      addCreditCard();
    }
  },[isSubmit, formMessages]);

  const validate = (values) => {
    const errors = {};
    if (!values.name) {
      errors.name = "name is required!";
    }
    if (!values.cardNumber || values.cardNumber.length === 0) {
      errors.cardNumber = "Card Number is required!";
    } 
    if (values.cardNumber && isNaN(values.cardNumber)){
        errors.cardNumber = "Card Number should be have numbers!";
    } 
    if (!values.limit) {
      errors.limit = "Limit is required";
    } else if (values.limit.length < 0) {
      errors.limit = "Limit can not be negative";
    } 
    return errors;
  };

  return (
    
    <div className="container">
      <form onSubmit={handleSubmit}>
        <h1>Credit Card System</h1>
        <label>Add</label>
        <div className="ui divider"></div>
        <div className="ui form">
         
            <label >Name </label>
            <input
              type="text"
              name="name"
              placeholder="Name"
              value={formValues.name}
              onChange={handleChange}
            />
          <p style={{color:"red"}}>{formMessages.name}</p>
        
            <label>Card Number</label>&nbsp;
            <input
              type="text"
              name="cardNumber"
              placeholder="Card Number"
              value={formValues.cardNumber}
              onChange={handleChange}
            />
         
          <p style={{color:"red"}}>{formMessages.cardNumber}</p>
            <label>Limit</label>
            <input
              type="text"
              name="limit"
              placeholder="Limit"
              value={formValues.limit}
              onChange={handleChange}
            />
          <p style={{color:"red"}}>{formMessages.limit}</p>
          <button className="fluid ui button blue">Add</button>
          {formMessages.errorMessage ?  (<p style={{color:"red"}}>{formMessages.errorMessage}</p>) : (<p style={{color:"green"}}>{formMessages.successMessage}</p>)}
        </div>
      </form>
    </div>
  );
}


export default AddCreditCard;