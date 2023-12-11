import "./App.css";
import ToDoRowItem from "./components/TodoRowItem";

function App() {
  return (
    <div className="mt-5 container">
      <div className="card">
        <div className="card-header">Your Todo's</div>
        <div className="card-body">
          <table className="table table-hover">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Description</th>
                <th scope="col">Assigned</th>
              </tr>
            </thead>
            <tbody>
              <ToDoRowItem />
              <tr>
                <th scope="row">2</th>
                <td>Hair Cut</td>
                <td>Eric</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default App;
