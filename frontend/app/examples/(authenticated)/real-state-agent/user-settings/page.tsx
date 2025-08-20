import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import UserSettingsPage from '@/components/pages/UserSettingsPage';
import React from 'react';

const UserSettings = () => {
    return (
        <>
            <HeaderDefault />
            <UserSettingsPage />
            <Footer />
        </>
    );
};

export default UserSettings;