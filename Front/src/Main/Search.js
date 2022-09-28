import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

function Search() {
  
  // const filtered = realData.filter((itemList) => {
  //   return itemList.cookery.toUpperCase().includes(input.toUpperCase());
  // })
    return (
      
<Form className="d-flex">
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
            />
            <Button variant="outline-success">Search</Button>
          </Form>
    )
}
export default Search;