import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import DashboardLandlordPage from '@/components/pages/DashboardPage/LandlordPage';
import React from 'react';

const DashboardLandlord = () => {
    return (
        <>
            <HeaderDefault />
            <DashboardLandlordPage />
            <Footer />
        </>
    );
};

export default DashboardLandlord;