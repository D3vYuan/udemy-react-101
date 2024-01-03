import BookModel from "./BookModel";

class ShelfCurrentLoansModels {
  book: BookModel;
  daysLeft: number;

  constructor(book: BookModel, daysLeft: number) {
    this.book = book;
    this.daysLeft = daysLeft;
  }
}

export default ShelfCurrentLoansModels;
