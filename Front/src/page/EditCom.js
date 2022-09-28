function EditCom({r, inputText, setInputText}){
  return(
    <div>
      <input
      type="text"
      value={inputText}
      placeholder={r.content}
      onChange={(e) => {
        setInputText(e.target.value);
      }}></input>
    </div>
  )
}
export default EditCom;


