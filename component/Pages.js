import Pagination from "react-bootstrap/Pagination";

/*
const Pages = () => {
  // 페이지를 나누는 메서드----------------------------------------------
  const currentPageData = tableData.slice(
    (currentPage - 1) * ITEMS_PER_PAGE,
    currentPage * ITEMS_PER_PAGE
  );
  const [showingNum, setShowingNum] = useState({
    start: 1,
    end: PAGES_PER_LIST,
  });

  useEffect(() => {
    const lessThanFive = totalPage <= PAGES_PER_LIST;
    lessThanFive
      ? setShowingNum((prev) => ({ ...prev, start: 1, end: totalPage }))
      : setShowingNum((prev) => ({ ...prev, start: 1, end: PAGES_PER_LIST }));
  }, [totalPage]);
  //화살표로 페이지 이동-------------------------------------------------------
  const changePageNumbersBackward = () => {
    currentPage > PAGES_PER_LIST &&
      setShowingNum((prev) => arrowHandler(prev, -1, totalPage));
  };

  const changePageNumbersForward = () => {
    showingNum.end <= PAGES_PER_LIST &&
      setShowingNum((prev) => arrowHandler(prev, 1, totalPage));
  };
  //----------------------------------------------------------------------------

  const arrowHandler = (prev, sign, totalPage) => {
    const PAGES_PER_LIST = 5;
    const nextIndex = prev.end + PAGES_PER_LIST;
    let res;
    if (sign === 1) {
      res = nextIndex > totalPage ? totalPage : nextIndex;
    } else if (sign === -1) {
      res =
        prev.end - prev.start < 4
          ? prev.start + 4 - PAGES_PER_LIST
          : prev.end - PAGES_PER_LIST;
    }
    return { ...prev, start: prev.start + PAGES_PER_LIST * sign, end: res };
  };
  return (
    <div>
      <input>{changePageNumbersBackward}</input>
      <input>{changePageNumbersForward}</input>
    </div>
  );
};
export default Pages;

*/
