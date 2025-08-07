import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import PurchaseOrRentPage from '@/components/pages/PurchaseOrRentPage';
import React from 'react';

const PurchaseOrRent = () => {
    return (
        <>
            <HeaderDefault />
            <PurchaseOrRentPage />
            <Footer />
        </>
    );
};

export default PurchaseOrRent;