import './App.css';
import GetAllCreditCardDetails from './components/GetAllCreditCardDetails'
import AddCreditCard from './components/AddCreditCard';

function App() {
  return (
    <div className="App" style={{
      backgroundColor: '#D3D3D3'
    }}>
      <AddCreditCard/>
      <br/>
      <GetAllCreditCardDetails />
    </div>
  );
}

export default App;
