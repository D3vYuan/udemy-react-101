export const Footer = () => {
  return (
    <div className="main-colr">
      <footer className="contianer d-flex flex-wrap justify-content-center align-items-center py-5 main-color">
        <p className="col-md-4 mb-0 text-white">Mao Library App</p>
        <ul className="nav navbar-dark col-md-4 justify-content-end">
          <li className="nav-item">
            <a className="nav-link px-2 text-white" href="#">
              Home
            </a>
          </li>
          <li className="nav-item">
            <a className="nav-link px-2 text-white" href="#">
              Search Books
            </a>
          </li>
        </ul>
      </footer>
    </div>
  );
};
