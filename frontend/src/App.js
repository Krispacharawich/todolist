import './App.css';
import TaskContainer from './components/TaskContainer';

const navbarStyle = {
  position: 'fixed',
  top: 0,
  left: 0,
  right: 0,
  height: '60px',
  backgroundColor: '#ffffff',
  boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  zIndex: 1000,
};

const titleStyle = {
  fontSize: '1.5rem',
  fontWeight: 'bold',
  color: '#333',
};
function Navbar() {
  return (
    <nav style={navbarStyle}>
      <h1 style={titleStyle}>TodoList</h1>
    </nav>
  );
}
function App() {

  return (
    <div className="App">
      <Navbar />
      <TaskContainer />
    </div>
  );
}

export default App;
