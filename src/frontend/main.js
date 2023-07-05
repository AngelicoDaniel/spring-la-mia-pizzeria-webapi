const PIZZE_API_URL = "http://localhost:8080/api/v1/pizze";
const contentDOM = document.getElementById("content");


console.log(axios);


const getPizze = async () => {
  try {
    const response = await axios.get(PIZZE_API_URL);
    console.log(response);
    return response.data;
  } catch (error) {
    console.log(error);
  }
};


const createPizzaList = (data) => {
  if (data.length > 0) {
    let list = "<ul>";
    data.forEach((element) => {
      list += `<li>${element.name}</li>`;
    });
    list += "</ul>";
    return list;
  } else {
    return '<div class="alert alert-info">La Lista Pizze è vuota</div>';
  }
};

const loadData = async () => {
  const data = await getPizze();
  contentDOM.innerHTML = createPizzaList(data);
};


getPizze();
loadData();