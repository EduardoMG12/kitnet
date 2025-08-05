import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import PricingGuidePage from '@/components/pages/PricingGuide';
import React from 'react';

const PricingGuide = () => {
    return (
        <>
            <HeaderDefault />
            <PricingGuidePage />
            <Footer />
        </>
    );
};

export default PricingGuide;