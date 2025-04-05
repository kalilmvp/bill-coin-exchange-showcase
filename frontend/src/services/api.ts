import axios from 'axios';

const API_URL = 'http://localhost:8080/api/bill-coin-exchange';

export const exchangeLeast = async (value: number) => {
    const response = await axios.post(`${API_URL}/least`, { value });
    return response.data;
};

export const exchangeMost = async (value: number) => {
    const response = await axios.post(`${API_URL}/most`, { value });
    return response.data;
};

export const initializeMachine = async () => {
    const response = await axios.put(`${API_URL}/init`);
    return response.data;
};
