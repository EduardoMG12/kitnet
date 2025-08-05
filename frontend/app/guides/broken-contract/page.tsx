import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import BrokenContractPage from '@/components/pages/BrokenContractPage';
import React from 'react';

const BrokenContract = () => {
    return (
        <>
            <HeaderDefault />
            <BrokenContractPage />
            <Footer />
        </>
    );
};

export default BrokenContract;