'use client';

interface MainLayoutProps {
    children: React.ReactNode;
    hideFooter?: boolean;
}

const MainLayout: React.FC<MainLayoutProps> = ({ children, hideFooter }) => {
    return (
        <div className="flex flex-col min-h-full h-full">
            {/*  Your Header component */}
            <main className="flex-grow h-full">
                {children}
            </main>
            {/* {!hideFooter && <Footer />} Your Footer component */}
        </div>
    );
};

export default MainLayout;