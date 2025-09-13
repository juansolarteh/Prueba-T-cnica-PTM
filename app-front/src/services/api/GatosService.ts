import axios from "axios";

const API_URL = "https://meowfacts.herokuapp.com";

export const GatosService = {
  getInformacionGatos: async (): Promise<string[]> => {
    const response = await axios.get(API_URL, {params: { count: 2, lang: "esp" }});
    return response.data.data;
  }
};