import axios from "axios";

const API_URL = "https://uselessfacts.jsph.pl/api/v2/facts/today";

export const DatoDiaService = {
  getDatoDia: async (): Promise<string> => {
    const response = await axios.get(API_URL);
    return response.data.text;
  }
};