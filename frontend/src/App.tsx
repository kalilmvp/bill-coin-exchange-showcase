import React, { useState } from 'react';
import { exchangeLeast, exchangeMost, initializeMachine } from './services/api';
import './App.css';

const App: React.FC = () => {
    const [value, setValue] = useState<number>(0);
    const [result, setResult] = useState<any>(null);
    const [error, setError] = useState<string | null>(null);

    const handleExchangeLeast = async () => {
        try {
            const data = await exchangeLeast(value);
            setResult(data);
            setError(null);
        } catch (err: any) {
            setError(err.response?.data?.message || 'An error occurred');
            setResult(null);
        }
    };

    const handleExchangeMost = async () => {
        try {
            const data = await exchangeMost(value);
            setResult(data);
            setError(null);
        } catch (err: any) {
            setError(err.response?.data?.message || 'An error occurred');
            setResult(null);
        }
    };

    const handleInitialize = async () => {
        try {
            const data = await initializeMachine();
            setResult(data);
            setError(null);
        } catch (err: any) {
            setError(err.response?.data?.message || 'An error occurred');
            setResult(null);
        }
    };

    return (
        <div className="app-container">
            <h1>Bill Coin Exchange</h1>
            <div className="input-container">
                <input
                    type="number"
                    value={value}
                    onChange={(e) => setValue(Number(e.target.value))}
                    placeholder="Enter value"
                />
                <button onClick={handleExchangeLeast}>Exchange Least</button>
                <button onClick={handleExchangeMost}>Exchange Most</button>
                <button onClick={handleInitialize}>Initialize Machine</button>
            </div>
            {error && (
                <div className="error-message">
                    <p>{error}</p>
                </div>
            )}
            {result && (
                <div className="result-container">
                    <h2>Result</h2>
                    <div className="result-section">
                        <h3>Amount in Dollars</h3>
                        <p>${result.amountDollars}</p>
                    </div>
                    <div className="result-section">
                        <h3>Coins</h3>
                        <table>
                            <thead>
                            <tr>
                                <th>Total Coins</th>
                                <th>Cents</th>
                                <th>Dollars Correspondent</th>
                            </tr>
                            </thead>
                            <tbody>
                            {result.coins.map((coin: any, index: number) => (
                                <tr key={index}>
                                    <td>{coin.totalCoins}</td>
                                    <td>{coin.cents}</td>
                                    <td>${coin.dollarsCorrespondent}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="result-section">
                        <h3>State</h3>
                        <p>Amount in Dollars: ${result.state.amountInDollars}</p>
                        <p>Total Amount: ${result.state.totalAmount}</p>
                        <p>Coins Amount: {result.state.coinsAmount}</p>
                        <table>
                            <thead>
                            <tr>
                                <th>Coin Count</th>
                                <th>Coin Value</th>
                                <th>Total Cents</th>
                            </tr>
                            </thead>
                            <tbody>
                            {result.state.coins.map((coin: any, index: number) => (
                                <tr key={index}>
                                    <td>{coin.coinCount}</td>
                                    <td>{coin.coinValue}</td>
                                    <td>{coin.totalCents}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}
        </div>
    );
};

export default App;
