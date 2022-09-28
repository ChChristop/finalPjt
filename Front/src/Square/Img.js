import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Like from './Like';


function Img() {
  return (
    <Card style={{ width: '18rem' }}>
      <Card.Img variant="top" src="Img/사진1.jpg" />
      <Card.Body>
        <Card.Title>감자탕</Card.Title>
        <Card.Text>
          Some quick example text to build on the card title and make up the
          bulk of the card's content.
        </Card.Text><Like/>
        <Button variant="primary"></Button>
      </Card.Body>
    </Card>
  );
}

export default Img;