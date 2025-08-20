import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import DashboardTenantPage from '@/components/pages/DashboardPage/TenantPage';
import React from 'react';

const DashboardTenant = () => {
    return (
        <>
            <HeaderDefault />
            <DashboardTenantPage />
            <Footer />
        </>

    );
};

export default DashboardTenant;