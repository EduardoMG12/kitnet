import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import PropertyInspectionPage from '@/components/pages/PropertyInspectionPage';
import React from 'react';

const PropertyInspection = () => {
    return (
        <>
            <HeaderDefault />
            <PropertyInspectionPage />
            <Footer />
        </>
    );
};

export default PropertyInspection;