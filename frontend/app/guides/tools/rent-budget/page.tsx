import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import RentBudgetPage from '@/components/pages/RentBudgetPage';
import React from 'react';

const RentBudget = () => {
    return (
        <>
            <HeaderDefault />
            <RentBudgetPage />
            <Footer />
        </>
    );
};

export default RentBudget;