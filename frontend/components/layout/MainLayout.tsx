'use client';

interface MainLayoutProps {
    children: React.ReactNode;
    hideFooter?: boolean;
}

const MainLayout: React.FC<MainLayoutProps> = ({ children, hideFooter }) => {
    return (
        <div className="flex flex-col min-h-screen">
            {/* <Header /> Your Header component */}
            <main className="flex-grow">
                {children}
            </main>
            {/* {!hideFooter && <Footer />} Your Footer component */}
        </div>
    );
};

export default MainLayout;