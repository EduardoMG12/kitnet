import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import RentalCalculatorPage from '@/components/pages/RentalCalculatorPage';
import React from 'react';

const RentalCalculator = () => {
    return (
        <>
            <HeaderDefault />
            <RentalCalculatorPage />
            <Footer />
        </>
    );
};

export default RentalCalculator;