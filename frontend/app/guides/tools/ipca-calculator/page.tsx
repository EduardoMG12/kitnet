import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import IpcaCalculatorPage from '@/components/pages/IpcaCalculatorPage';
import React from 'react';

const IpcaCalculator = () => {
    return (
        <>
            <HeaderDefault />
            <IpcaCalculatorPage />
            <Footer />
        </>
    );
};

export default IpcaCalculator;