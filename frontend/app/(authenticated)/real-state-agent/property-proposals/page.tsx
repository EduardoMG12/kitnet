import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import PropertyProposalsPage, { type Proposal } from '@/components/pages/PropertyProposalsPage';
import React from 'react';


const PropertyProposal = () => {
    return (
        <>
            <HeaderDefault />
            <PropertyProposalsPage />
            <Footer />
        </>
    );
};

export default PropertyProposal;