import { useEffect, useState } from "react";
import ReviewModel from "../../../models/ReviewModel";
import { Pagination } from "../../Utils/Pagination";
import { Review } from "../../Utils/Review";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";

export const ReviewListPage = () => {
  const [reviews, setReviews] = useState<ReviewModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState(null);

  // Pagination
  const [currentPage, setCurrentPage] = useState(1);
  const [reviewsPerPage] = useState(5);
  const [totalAmountOfReviews, setTotalAmountOfReviews] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const bookId = window.location.pathname.split("/")[2];

  useEffect(() => {
    const fetchReviews = async () => {
      const reviewUrl: string = `${
        process.env.REACT_APP_API
      }/reviews/search/findByBookId?bookId=${bookId}&page=${
        currentPage - 1
      }&size=${reviewsPerPage}`;
      const response = await fetch(reviewUrl);
      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJson = await response.json();
      const responseData = responseJson._embedded.reviews;

      const loadedReviews: ReviewModel[] = [];

      setTotalAmountOfReviews(responseJson.page.totalElements);
      setTotalPages(responseJson.page.setTotalPages);

      for (const key in responseData) {
        loadedReviews.push({
          id: responseData[key].id,
          userEmail: responseData[key].userEmail,
          date: responseData[key].date,
          rating: responseData[key].rating,
          bookId: responseData[key].bookId,
          reviewDescription: responseData[key].reviewDescription,
        });
      }

      setReviews(loadedReviews);
      setIsLoading(false);
    };
    fetchReviews().catch((error: any) => {
      setIsLoading(false);
      setHttpError(error.message);
    });
  }, [currentPage]); // If state inside array changes will trigger the useEffect

  if (isLoading) {
    return <SpinnerLoading />;
  }

  if (httpError) {
    return (
      <div className="container m-5">
        <p>{httpError}</p>
      </div>
    );
  }

  const indexOfLastReview: number = currentPage * reviewsPerPage;
  const indexOfFirstReview: number = indexOfLastReview - reviewsPerPage;

  let lastItem =
    reviewsPerPage * currentPage <= totalAmountOfReviews
      ? reviewsPerPage * currentPage
      : totalAmountOfReviews;

  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

  return (
    <div className="container m-5">
      <div>
        <h3>Comments: ({reviews.length})</h3>
      </div>
      <p>
        {indexOfFirstReview + 1} to {lastItem} of {totalAmountOfReviews} items:
      </p>
      <div className="row">
        {reviews.map((review) => (
          <Review review={review} key={review.id} />
        ))}
      </div>

      {totalPages > 1 && (
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          paginate={paginate}
        />
      )}
    </div>
  );
};
