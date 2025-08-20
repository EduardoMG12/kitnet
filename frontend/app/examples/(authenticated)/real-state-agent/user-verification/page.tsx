import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import UserVerificationPage from '@/components/pages/UserVerificationPage';
import React from 'react';

const UserVerification = () => {
    return (
        <>
            <HeaderDefault />
            <UserVerificationPage />
            <Footer />
        </>
    );
};

export default UserVerification;